package com.liyz.boot3.service.auth.provider;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.liyz.boot3.common.service.constant.CommonServiceConstant;
import com.liyz.boot3.common.util.DateUtil;
import com.liyz.boot3.common.util.PatternUtil;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.constants.AuthConstants;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.auth.enums.LoginType;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import com.liyz.boot3.service.auth.exception.RemoteAuthServiceException;
import com.liyz.boot3.service.auth.model.AuthJwtDO;
import com.liyz.boot3.service.auth.remote.RemoteAuthService;
import com.liyz.boot3.service.auth.remote.RemoteJwtParseService;
import com.liyz.boot3.service.auth.service.AuthJwtService;
import com.liyz.boot3.service.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @Resource
    private RedissonClient redissonClient;

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
            log.error("解析token失败, 没有找到该应用下jwt配置信息，clientId：{}", clientId);
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        if (StringUtils.isBlank(token) || !token.startsWith(authJwtDO.getJwtPrefix())) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        final String authToken = token.substring(authJwtDO.getJwtPrefix().length()).trim();
        Claims unSignClaims;
        try {
            unSignClaims = this.parseClaimsJws(authToken);
        } catch (Exception e) {
            log.error("解析token失败  token:{}", authToken);
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        Set<String> audience = unSignClaims.getAudience();
        if (CollectionUtils.isEmpty(audience) || audience.size() != 3) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        List<String> audienceList = audience.stream().toList();
        if (!clientId.equals(audienceList.stream().findFirst().orElse(StringUtils.EMPTY))) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        Claims claims = this.parseClaimsJws(authToken, Joiner.on(CommonServiceConstant.DEFAULT_PADDING)
                .join(authJwtDO.getSigningKey(), CollectionUtils.lastElement(audienceList)));
        RSet<Object> set = redissonClient.getSet(AuthConstants.getRedisKey(clientId, claims.getId()));
        if (DateUtil.currentDate().compareTo(claims.getExpiration()) > 0 || !set.isExists()) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_TIMEOUT);
        }
        if (authJwtDO.getOneOnline() && !set.contains(audienceList.get(1))) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.OTHERS_LOGIN);
        }
        if (!clientId.equals(claims.getAudience().stream().findFirst().orElse(StringUtils.EMPTY))) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        AuthUserBO authUserBO = AuthUserBO.builder()
                .username(claims.getSubject())
                .password(StringUtils.EMPTY)
                .salt(StringUtils.EMPTY)
                .loginType(LoginType.getByType(PatternUtil.checkMobileEmail(claims.getSubject())))
                .device(Device.getByType(unSignClaims.get(CLAIM_DEVICE, Integer.class)))
                .authId(Long.valueOf(claims.getId()))
                .roleIds(Lists.newArrayList())
                .token(authToken)
                .loginKey(audienceList.get(1))
                .clientId(claims.getAudience().stream().findFirst().orElse(StringUtils.EMPTY))
                .build();
        authUserBO.setAuthorities(authJwtDO.getIsAuthority() ? remoteAuthService.authorities(authUserBO) : Lists.newArrayList());
        return authUserBO;
    }

    /**
     * 生成token
     *
     * @param authUser 用户信息
     * @return jwt token
     */
    @Override
    public Pair<String, String> generateToken(AuthUserBO authUser) {
        if (StringUtils.isBlank(authUser.getClientId())) {
            log.error("创建token失败，原因 : clientId is blank");
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        AuthJwtDO authJwtDO = authJwtService.getByClientId(authUser.getClientId());
        if (Objects.isNull(authJwtDO)) {
            log.error("生成token失败, 没有找到该应用下jwt配置信息，clientId : {}", authUser.getClientId());
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        return Pair.ofNonNull(authJwtDO.getJwtPrefix(),
                JwtUtil.builder()
                        .id(authUser.getAuthId().toString())
                        .subject(authUser.getUsername())
                        .audience().add(authUser.getClientId()).add(authUser.getLoginKey()).add(authUser.getSalt()).and()
                        .expiration(new Date(System.currentTimeMillis() + authJwtDO.getExpiration() * 1000))
                        .claim(CLAIM_DEVICE, authUser.getDevice().getType())
                        .signWith(
                                SignatureAlgorithm.forName(authJwtDO.getSignatureAlgorithm()),
                                Keys.hmacShaKeyFor(Decoders.BASE64.decode(Joiner.on(CommonServiceConstant.DEFAULT_PADDING).join(authJwtDO.getSigningKey(), authUser.getSalt())))
                        )
                        .compact());
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
            claims = JwtUtil.parser().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
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
        return JwtUtil.decode(token, DefaultClaims.class);
    }
}
