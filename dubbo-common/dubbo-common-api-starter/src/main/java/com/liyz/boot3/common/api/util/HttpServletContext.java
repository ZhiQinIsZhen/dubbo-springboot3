package com.liyz.boot3.common.api.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:02
 */
@UtilityClass
public class HttpServletContext {

    /**
     * 获取servlet请求
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * 获取response请求
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    /**
     * 获取ip地址
     *
     * @return ip地址
     */
    public static String getIpAddress() {
        return getIpAddress(getRequest());
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     *
     * @param request HttpServletRequest
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ipAddress)|| "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("ProxyAop-Client-IP");
        }
        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-ProxyAop-Client-IP");
        }
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress.split(",")[0];
    }
}
