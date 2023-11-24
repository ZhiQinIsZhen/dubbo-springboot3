package com.liyz.boot3.service.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:12
 */
@Getter
@AllArgsConstructor
public enum LoginType {

    MOBILE(1, "手机号码登录"),
    EMAIL(2, "邮箱登录"),
    ;

    private final int type;
    private final String desc;

    public static LoginType getByType(int type) {
        for (LoginType loginType : LoginType.values()) {
            if (type == loginType.type) {
                return loginType;
            }
        }
        return null;
    }
}
