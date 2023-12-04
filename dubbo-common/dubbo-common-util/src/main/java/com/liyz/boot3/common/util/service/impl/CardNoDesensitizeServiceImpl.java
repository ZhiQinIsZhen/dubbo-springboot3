package com.liyz.boot3.common.util.service.impl;

import com.liyz.boot3.common.util.annotation.Desensitization;
import com.liyz.boot3.common.util.constant.DesensitizationType;
import com.liyz.boot3.common.util.service.DesensitizeService;
import org.apache.commons.lang3.StringUtils;

/**
 * 注释:身份证脱敏
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2022/4/20 19:44
 */
public class CardNoDesensitizeServiceImpl implements DesensitizeService {

    private static final String Card_No_Regex = "(\\d{4})\\d{10}(\\w{4})";

    @Override
    public DesensitizationType getType() {
        return DesensitizationType.CARD_NO;
    }

    @Override
    public String desensitize(String value, Desensitization annotation) {
        if (StringUtils.isNotBlank(value)) {
            if (value.length() == 18) {
                value = value.replaceAll(Card_No_Regex, "$1****$2");
            } else {
                value = String.format("%s****%s", value.substring(0, 4), value.substring(14));
            }
        }
        return value;
    }
}
