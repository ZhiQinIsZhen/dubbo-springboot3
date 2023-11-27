package com.liyz.boot3.api.admin.vo.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 16:13
 */
@Getter
@Setter
public class AuthLoginVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1353574234588192865L;

    @Schema(description = "鉴权token")
    private String token;

    @Schema(description = "Token失效时间戳(ms)")
    private Long expiration;
}
