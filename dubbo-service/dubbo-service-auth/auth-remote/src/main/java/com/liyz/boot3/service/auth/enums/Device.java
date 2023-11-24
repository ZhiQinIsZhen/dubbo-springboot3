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
public enum Device {
    MOBILE(1, "移动端"),
    WEB(2, "网页端"),
    ;

    private final int type;
    private final String desc;

    public static Device getByType(int type) {
        for (Device device : Device.values()) {
            if (type == device.type) {
                return device;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type + ":" + desc;
    }
}
