package com.liyz.boot3.api.user.vo.export;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/12 10:08
 */
@Getter
@Setter
public class SscPageVO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -7866058511913991568L;

    private String code;

    private String message;

    private Data<T> data;

    @Getter
    @Setter
    public static class Data<T> implements Serializable {
        @Serial
        private static final long serialVersionUID = -4156339185088627049L;

        private Long total;

        private List<T> items;
    }
}
