package com.liyz.boot3.api.test.controller.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyz.boot3.common.util.JsonMapperUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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
    public static class Apple {

        private Integer colour;

        @JsonIgnore
        private String name;
    }

    public static void main(String[] args) throws URISyntaxException {
        Apple apple = new Apple();
        apple.setColour(1);
        apple.setName("红富士");
        System.out.println(JsonMapperUtil.toJSONPrettyString(apple));
        URI uri = new URI("https://outin-72ca006e381a11eea37e00163e06123c.oss-cn-shanghai.aliyuncs.com/102dd85d6fef71efa7625017f1e80102/bf1c38c99719412481cd5956d89e958e-aa240fe11e2b3979a8471e3c214f7706-ld.m3u8?Expires=1730259486&OSSAccessKeyId=LTAIxSaOfEzCnBOj&Signature=oYQqrwWvNAu1x7rzia3K7%2B%2BmrfA%3D&x-oss-process=hls%2Fsign");
        List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);
        for (NameValuePair item : nameValuePairs) {
            System.out.println(JsonMapperUtil.toJSONPrettyString(item));
        }
    }
}
