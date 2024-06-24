package com.liyz.boot3.security.client.filter;

import com.google.common.base.Charsets;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.api.util.CookieUtil;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;
import com.liyz.boot3.common.util.DateUtil;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.security.client.config.AnonymousMappingConfig;
import com.liyz.boot3.security.client.constant.SecurityClientConstant;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.security.client.user.AuthUserDetails;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:34
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final String tokenHeaderKey;

    public JwtAuthenticationTokenFilter(String tokenHeaderKey) {
        this.tokenHeaderKey = tokenHeaderKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = CookieUtil.getCookie(this.tokenHeaderKey);
        //UriUtils、URLDecoder、URLEncoder
        String token = Objects.isNull(cookie) ? request.getHeader(this.tokenHeaderKey) : UriUtils.decode(cookie.getValue(), StandardCharsets.UTF_8);
        try {
            if (!AnonymousMappingConfig.pathMatch(request.getServletPath()) && StringUtils.isNotBlank(token)) {
                token = URLDecoder.decode(token, String.valueOf(Charsets.UTF_8));
                final AuthUserBO authUser = AuthContext.JwtService.parseToken(token);
                AuthUserDetails authUserDetails = AuthUserDetails.build(authUser);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        authUserDetails,
                        null,
                        authUserDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                AuthContext.setAuthUser(authUser);
            }
            //cookie续期
            if (Objects.nonNull(cookie)) {
                boolean renewal = true;
                //todo 过期时间实际是要配置的
                int expiry = 30 * 60;
                Cookie extraCookie = CookieUtil.getCookie(this.tokenHeaderKey + CookieUtil.COOKIE_START_SUFFIX);
                if (Objects.nonNull(extraCookie) && (expiry/2*1000) > (DateUtil.currentDate().getTime() - Long.parseLong(extraCookie.getValue()))) {
                    renewal = false;
                }
                if (renewal) {
                    CookieUtil.addCookie(
                            response,
                            SecurityClientConstant.DEFAULT_TOKEN_HEADER_KEY,
                            token,
                            expiry,
                            null
                    );
                }
            }
            //处理下一个过滤器
            filterChain.doFilter(request, response);
        } catch (RemoteServiceException exception) {
            response.setCharacterEncoding(Charsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().println(JsonMapperUtil.toJSONString(Result.error(exception.getCode(), exception.getMessage())));
            response.getWriter().flush();
        } finally {
            AuthContext.remove();
            SecurityContextHolder.getContextHolderStrategy().clearContext();
            SecurityContextHolder.clearContext();
        }
    }
}
