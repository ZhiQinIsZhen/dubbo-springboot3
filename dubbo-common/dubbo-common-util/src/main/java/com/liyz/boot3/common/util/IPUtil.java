package com.liyz.boot3.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Desc:Get IP address tool class
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 14:46
 */
@Slf4j
@UtilityClass
public class IPUtil {

    private static final String OS_NAME_KEY = "os.name";
    private static final String WINDOWS_OS_NAME = "windows";

    /**
     * 获取本机ip地址
     * 注：自动区分Windows还是linux操作系统
     *
     * @return
     */
    public static String getLocalIP() {
        InetAddress inetAddress = null;
        boolean windowsOS = isWindowsOS();
        try {
            if (windowsOS) {
                inetAddress = InetAddress.getLocalHost();
            } else {
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                k1:
                while (netInterfaces.hasMoreElements()) {
                    NetworkInterface ni = netInterfaces.nextElement();
                    //遍历所有IP
                    Enumeration<InetAddress> ips = ni.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        inetAddress = ips.nextElement();
                        if (inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().indexOf(':') == -1) {
                            break k1;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("get IP address fail");
        }
        return inetAddress.getHostAddress();
    }

    /**
     * 是否是windows系统
     *
     * @return
     */
    private boolean isWindowsOS() {
        String osName = System.getProperty(OS_NAME_KEY);
        log.debug("get os name : {}", osName);
        return osName.toLowerCase().indexOf(WINDOWS_OS_NAME) > -1 ? true : false;
    }
}
