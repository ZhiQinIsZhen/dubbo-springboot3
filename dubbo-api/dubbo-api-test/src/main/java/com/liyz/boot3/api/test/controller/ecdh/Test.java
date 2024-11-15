package com.liyz.boot3.api.test.controller.ecdh;

import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author LiYangzhen
 * @version : Test.java, v 0.1 2024-11-13 15:54 LiYangzhen Exp $
 */
public class Test {

    public static void main(String[] args) {
        String a = Base64.getUrlEncoder().encodeToString("Hello 图片服务!".getBytes(StandardCharsets.UTF_8));

        String b = UriUtils.encode("Hello, 图片服务！", StandardCharsets.UTF_8);
        System.out.println(a);
        System.out.println(b);
        System.out.println(new String(Base64.getUrlDecoder().decode("SGVsbG8g5Zu-54mH5pyN5YqhIQ"), StandardCharsets.UTF_8));
        System.out.println(Base64.getUrlEncoder().encodeToString("仅平台公示使用".getBytes(StandardCharsets.UTF_8)));
    }
}
