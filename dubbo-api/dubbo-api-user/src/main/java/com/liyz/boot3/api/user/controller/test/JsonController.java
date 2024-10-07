package com.liyz.boot3.api.user.controller.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyz.boot3.common.util.JsonMapperUtil;
import lombok.Getter;
import lombok.Setter;

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

    public static void main(String[] args) {
        Apple apple = new Apple();
        apple.setColour(1);
        apple.setName("红富士");
        System.out.println(JsonMapperUtil.toJSONPrettyString(apple));

    }
}
