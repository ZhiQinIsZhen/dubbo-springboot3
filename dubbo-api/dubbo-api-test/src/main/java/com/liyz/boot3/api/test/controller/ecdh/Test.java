package com.liyz.boot3.api.test.controller.ecdh;

import com.google.common.collect.Lists;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.common.util.RandomUtil;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * @author LiYangzhen
 * @version : Test.java, v 0.1 2024-11-13 15:54 LiYangzhen Exp $
 */
public class Test {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    private static void test3() {
        System.out.println(RandomUtil.randomChars(32, RandomUtil.NUMBER_CODES, RandomUtil.LW_CASE_LETTERS));;

    }

    private static void test2() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);
        for (int i = 0; i < ((list.size()/9) + 1); i++) {
            int toIndex = Math.min(((i + 1) * 9), list.size());
            System.out.println(JsonMapperUtil.toJSONString(list.subList(i * 9, toIndex)));
        }
    }

    private static void test1() {
        System.out.println(System.currentTimeMillis());
        String a = Base64.getUrlEncoder().encodeToString("Hello 图片服务!".getBytes(StandardCharsets.UTF_8));

        String b = UriUtils.encode("Hello, 图片服务！", StandardCharsets.UTF_8);
        System.out.println(a);
        System.out.println(b);
        System.out.println(new String(Base64.getUrlDecoder().decode("SGVsbG8g5Zu-54mH5pyN5YqhIQ"), StandardCharsets.UTF_8));
        System.out.println(Base64.getUrlEncoder().encodeToString("仅平台公示使用".getBytes(StandardCharsets.UTF_8)));
    }
}
