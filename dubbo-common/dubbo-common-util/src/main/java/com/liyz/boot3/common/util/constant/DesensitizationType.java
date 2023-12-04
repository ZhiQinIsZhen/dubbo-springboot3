package com.liyz.boot3.common.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/4 13:27
 */
@Getter
@AllArgsConstructor
public enum DesensitizationType {
    DEFAULT("默认"),
    TRIM(""),
    MOBILE("手机号码"),
    EMAIL("邮箱地址"),
    REAL_NAME("真实姓名"),
    CARD_NO("身份证号码"),
    SELF_DEFINITION("自定义"),
    IGNORE("忽略"),
    ENCRYPT_DECRYPT("加密"),
    DFA("DFA算法"),
    ;

    private final String desc;
}
