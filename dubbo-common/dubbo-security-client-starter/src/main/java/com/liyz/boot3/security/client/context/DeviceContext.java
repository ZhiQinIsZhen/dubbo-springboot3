package com.liyz.boot3.security.client.context;

import com.liyz.boot3.service.auth.enums.Device;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:52
 */
@Slf4j
@UtilityClass
public class DeviceContext {

    /**
     * 获取设备类型
     *
     * @param request http request
     * @return 设备类型
     */
    public static Device getDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isBlank(userAgent)) {
            return Device.WEB;
        }
        userAgent = userAgent.toLowerCase();
        log.info("http header User-Agent : {}", userAgent);
        if (userAgent.contains("android")
                || userAgent.contains("mobile")
                || userAgent.contains("ipad")
                || userAgent.contains("iphone")
                || userAgent.contains("ipod")
                || userAgent.contains("silk")) {
            return Device.MOBILE;
        } else {
            return Device.WEB;
        }
    }
}
