package com.liyz.boot3.common.api.util;

import com.liyz.boot3.common.util.DateUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/21 15:47
 */
@UtilityClass
public class CookieUtil {

    /**
     * 获得指定cookie中的值
     *
     * @param request http request
     * @param cookieName cookie
     * @return 值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        return Objects.isNull(cookie) ? null : cookie.getValue();
    }

    /**
     * 获得指定cookie中的值
     *
     * @param cookieName cookie
     * @return 值
     */
    public static String getCookieValue(String cookieName) {
        return getCookieValue(HttpServletContext.getRequest(), cookieName);
    }

    /**
     * 获得指定cookie中的值
     *
     * @param request http request
     * @param cookieName cookie
     * @return 值
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 获得指定cookie中的值
     *
     * @param cookieName cookie
     * @return 值
     */
    public static Cookie getCookie(String cookieName) {
        return getCookie(HttpServletContext.getRequest(), cookieName);
    }

    /**
     * 添加一个cookie
     *
     * @param response http response
     * @param cookieName cookie
     * @param value 值
     * @param expiry 过期时间
     * @param domain 作用域
     */
    @SneakyThrows
    public static void addCookie(HttpServletResponse response, String cookieName, String value, int expiry, String domain) {
        Cookie cookie = new Cookie(cookieName, UriUtils.encode(value, StandardCharsets.UTF_8));
        cookie.setMaxAge(expiry);
        cookie.setSecure(false);
        cookie.setPath("/");
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setAttribute("start", String.valueOf(DateUtil.currentDate().getTime()));
        response.addCookie(cookie);
    }

    /**
     * 添加一个cookie
     *
     * @param cookieName cookie
     * @param value 值
     * @param time 过期时间
     * @param domain 作用域
     */
    public static void addCookie(String cookieName, String value, int time, String domain) {
        addCookie(HttpServletContext.getResponse(), cookieName, value, time, domain);
    }

    /**
     * 添加一个cookie
     *
     * @param response http response
     * @param cookie cookie
     */
    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    /**
     * 添加一个cookie
     *
     * @param cookie cookie
     */
    public static void addCookie(Cookie cookie) {
        addCookie(HttpServletContext.getResponse(), cookie);
    }

    /**
     * 移除cookie
     *
     * @param response http response
     * @param cookieName cookie
     */
    public static void removeCookie(HttpServletResponse response, String cookieName) {
        addCookie(response, cookieName, StringUtils.EMPTY, 0, null);
    }

    /**
     * 移除cookie
     *
     * @param cookieName cookie
     */
    public static void removeCookie(String cookieName) {
        removeCookie(HttpServletContext.getResponse(), cookieName);
    }
}
