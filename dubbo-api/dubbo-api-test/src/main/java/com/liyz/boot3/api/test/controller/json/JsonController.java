package com.liyz.boot3.api.test.controller.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyz.boot3.common.util.JsonMapperUtil;
import lombok.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/9/20 18:28
 */
public class JsonController {


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Apple {

        private BigDecimal price;

        private Integer colour;

        @JsonIgnore
        private String name;
    }

    public static void main(String[] args) {
        List<Apple> list = new ArrayList();
        list.add(Apple.builder().price(BigDecimal.valueOf(3)).colour(4).build());
        list.add(Apple.builder().price(BigDecimal.valueOf(5)).colour(1).build());
        list.add(Apple.builder().price(BigDecimal.valueOf(2)).colour(6).build());
        list.add(Apple.builder().price(BigDecimal.valueOf(3)).colour(1).build());
        list.add(Apple.builder().price(BigDecimal.valueOf(4)).colour(2).build());
        list.add(Apple.builder().price(BigDecimal.valueOf(2)).colour(0).build());
        list.add(Apple.builder().price(BigDecimal.valueOf(2)).colour(0).build());
        Apple object = list.stream().min(Comparator.comparing(Apple::getPrice).thenComparing(Apple::getColour)).orElse(null);
        System.out.println(JsonMapperUtil.toJSONString(object));
        object = list.stream().min(Comparator.comparing(Apple::getColour).thenComparing(Apple::getPrice)).orElse(null);
        System.out.println(JsonMapperUtil.toJSONString(object));
    }

    /*public static void main1(String[] args) throws URISyntaxException {
        Apple apple = new Apple();
        apple.setColour(1);
        apple.setName("红富士");
        System.out.println(JsonMapperUtil.toJSONPrettyString(apple));
        URI uri = new URI("https://outin-72ca006e381a11eea37e00163e06123c.oss-cn-shanghai.aliyuncs.com/102dd85d6fef71efa7625017f1e80102/bf1c38c99719412481cd5956d89e958e-aa240fe11e2b3979a8471e3c214f7706-ld.m3u8?Expires=1730259486&OSSAccessKeyId=LTAIxSaOfEzCnBOj&Signature=oYQqrwWvNAu1x7rzia3K7%2B%2BmrfA%3D&x-oss-process=hls%2Fsign");
        List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);
        for (NameValuePair item : nameValuePairs) {
            System.out.println(JsonMapperUtil.toJSONPrettyString(item));
        }
    }*/
}
