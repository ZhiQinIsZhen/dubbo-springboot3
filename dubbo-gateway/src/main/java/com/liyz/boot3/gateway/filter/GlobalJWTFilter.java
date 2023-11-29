package com.liyz.boot3.gateway.filter;

import com.liyz.boot3.common.remote.exception.RemoteServiceException;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.gateway.properties.AnonymousMappingProperties;
import com.liyz.boot3.gateway.util.ResponseUtil;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import com.liyz.boot3.service.auth.remote.RemoteJwtParseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/27 16:44
 */
@Slf4j
@Component
@EnableConfigurationProperties(AnonymousMappingProperties.class)
public class GlobalJWTFilter implements GlobalFilter {

    private final AnonymousMappingProperties properties;

    public GlobalJWTFilter(AnonymousMappingProperties properties) {
        this.properties = properties;
    }

    @DubboReference(group = "auth")
    private RemoteJwtParseService remoteJwtParseService;

    /**
     * 确认是否需要认证JWT
     * todo 这里是伪代码，真实情况根据自己的来
     *
     * @param exchange 数据
     * @param chain 链
     * @return 结果
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        ServerHttpResponse resp = exchange.getResponse();
        String path = req.getURI().getPath();
        log.info("path : {}", path);
        String clientId = new AntPathMatcher().match("/admin/**", path) ? "dubbo-api-admin" : "dubbo-api-user";
        Set<String> mappingSet = properties.getServer().get(clientId);
        if (CollectionUtils.isEmpty(mappingSet) || !mappingSet.contains(path)) {
            String jwt = req.getHeaders().getFirst("Authorization");
            if (StringUtils.isBlank(jwt)) {
                return ResponseUtil.response(resp, AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
            }
            log.info("JWT : {}", jwt);
            try {
                AuthUserBO authUserBO = remoteJwtParseService.parseToken(jwt, clientId);
                if (Objects.isNull(authUserBO)) {
                    return ResponseUtil.response(resp, AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
                }
                log.info("AuthUserBO : {}", JsonMapperUtil.toJSONString(authUserBO));
            } catch (RemoteServiceException e) {
                return ResponseUtil.response(resp, e.getCodeService());
            } catch (Exception e) {
                log.error("parse token error", e);
                return ResponseUtil.response(resp, AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
            }
        }
        return chain.filter(exchange);
    }
}
