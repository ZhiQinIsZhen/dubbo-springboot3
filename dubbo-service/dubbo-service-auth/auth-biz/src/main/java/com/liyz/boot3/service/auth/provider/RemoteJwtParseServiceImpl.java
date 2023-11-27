package com.liyz.boot3.service.auth.provider;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.liyz.boot3.common.service.constant.CommonServiceConstant;
import com.liyz.boot3.common.util.DateUtil;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.common.util.PatternUtil;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.auth.enums.LoginType;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import com.liyz.boot3.service.auth.exception.RemoteAuthServiceException;
import com.liyz.boot3.service.auth.model.AuthJwtDO;
import com.liyz.boot3.service.auth.remote.RemoteAuthService;
import com.liyz.boot3.service.auth.remote.RemoteJwtParseService;
import com.liyz.boot3.service.auth.service.AuthJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.jwt.JwtHelper;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 9:27
 */
@Slf4j
@DubboService
public class RemoteJwtParseServiceImpl implements RemoteJwtParseService {

    private final static String CLAIM_DEVICE = "device";

    @Resource
    private AuthJwtService authJwtService;
    @Resource
    private RemoteAuthService remoteAuthService;

    /**
     * 解析token
     *
     * @param token jwt token
     * @param clientId 应用名称
     * @return 用户信息
     */
    @Override
    public AuthUserBO parseToken(String token, String clientId) {
        AuthJwtDO authJwtDO = authJwtService.getByClientId(clientId);
        if (Objects.isNull(authJwtDO)) {
            log.warn("解析token失败, 没有找到该应用下jwt配置信息，clientId：{}", clientId);
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        if (StringUtils.isBlank(token) || !token.startsWith(authJwtDO.getJwtPrefix())) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        final String authToken = token.substring(authJwtDO.getJwtPrefix().length()).trim();
        Claims unSignClaims = parseClaimsJws(authToken);
        AuthUserBO authUser = remoteAuthService.loadByUsername(Joiner.on(CommonServiceConstant.DEFAULT_JOINER)
                .join(clientId, unSignClaims.getSubject()), Device.getByType(unSignClaims.get(CLAIM_DEVICE, Integer.class)));
        if (Objects.isNull(authUser)) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        Claims claims = this.parseClaimsJws(authToken, Joiner.on(CommonServiceConstant.DEFAULT_PADDING).join(authJwtDO.getSigningKey(), authUser.getSalt()));
        if (authJwtDO.getOneOnline() && Objects.nonNull(authUser.getCheckTime()) && claims.getNotBefore().compareTo(authUser.getCheckTime()) < 0) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.OTHERS_LOGIN);
        }
        if (!clientId.equals(claims.getAudience().stream().findFirst().get())) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        if (DateUtil.currentDate().compareTo(claims.getExpiration()) > 0) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_TIMEOUT);
        }
        return AuthUserBO.builder()
                .username(claims.getSubject())
                .password(StringUtils.EMPTY)
                .salt(StringUtils.EMPTY)
                .loginType(LoginType.getByType(PatternUtil.checkMobileEmail(claims.getSubject())))
                .device(Device.getByType(unSignClaims.get(CLAIM_DEVICE, Integer.class)))
                .authId(Long.valueOf(claims.getId()))
                .checkTime(claims.getNotBefore())
                .roleIds(Lists.newArrayList())
                .token(authToken)
                .clientId(claims.getAudience().stream().findFirst().get())
                .authorities(authJwtDO.getIsAuthority() ? remoteAuthService.authorities(authUser) : Lists.newArrayList())
                .build();
    }

    /**
     * 生成token  todo 为啥要把salt放置在bo里
     *
     * @param authUser 用户信息
     * @return jwt token
     */
    @Override
    public String generateToken(AuthUserBO authUser) {
        if (StringUtils.isBlank(authUser.getClientId())) {
            log.warn("创建token失败，原因 : clientId is blank");
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        AuthJwtDO authJwtDO = authJwtService.getByClientId(authUser.getClientId());
        if (Objects.isNull(authJwtDO)) {
            log.warn("生成token失败, 没有找到该应用下jwt配置信息，clientId : {}", authUser.getClientId());
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        return Jwts.builder()
                .id(authUser.getAuthId().toString())
                .subject(authUser.getUsername())
                .audience().add(authUser.getClientId()).and()
                .expiration(new Date(System.currentTimeMillis() + authJwtDO.getExpiration() * 1000))
                .notBefore(authUser.getCheckTime())
                .claim(CLAIM_DEVICE, authUser.getDevice().getType())
                .signWith(
                        SignatureAlgorithm.forName(authJwtDO.getSignatureAlgorithm()),
                        Keys.hmacShaKeyFor(Decoders.BASE64.decode(Joiner.on(CommonServiceConstant.DEFAULT_PADDING).join(authJwtDO.getSigningKey(), authUser.getSalt())))
                )
                .compact();
    }

    /**
     * 获取失效时间
     *
     * @param token jwt token
     * @return 失效时间
     */
    @Override
    public Long getExpiration(final String token) {
        return this.parseClaimsJws(token).getExpiration().getTime();
    }

    /**
     * 解析token
     *
     * @param token jwt token
     * @param signingKey 签名
     * @return 解析后属性
     */
    private Claims parseClaimsJws(final String token, final String signingKey) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        return claims;
    }

    /**
     * 解析token
     *
     * @param token jwt token
     * @return 解析后属性
     */
    private Claims parseClaimsJws(final String token) {
        return JsonMapperUtil.readValue(JwtHelper.decode(token).getClaims(), DefaultClaims.class);
    }
}
