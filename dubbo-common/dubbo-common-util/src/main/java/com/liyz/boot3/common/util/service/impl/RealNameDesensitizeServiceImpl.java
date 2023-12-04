package com.liyz.boot3.common.util.service.impl;

import com.liyz.boot3.common.util.annotation.Desensitization;
import com.liyz.boot3.common.util.constant.DesensitizationType;
import com.liyz.boot3.common.util.service.DesensitizeService;
import org.apache.commons.lang3.StringUtils;

/**
 * 注释:姓名脱敏
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2022/4/20 20:36
 */
public class RealNameDesensitizeServiceImpl implements DesensitizeService {

    private static final String Name_Regex = "(\\W)(\\w*)(\\W)";

    @Override
    public String desensitize(String value, Desensitization annotation) {
        int length;
        if (StringUtils.isNotBlank(value) && (length = value.length()) > 1) {
            value = StringUtils.left(value, 1) + StringUtils.leftPad(StringUtils.right(value, 1), length > 2 ? value.length() - 1 : length, "*");
        }
        return value;
    }

    @Override
    public DesensitizationType getType() {
        return DesensitizationType.REAL_NAME;
    }
}
