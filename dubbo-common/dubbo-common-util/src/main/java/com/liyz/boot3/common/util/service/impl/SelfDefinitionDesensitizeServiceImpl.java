package com.liyz.boot3.common.util.service.impl;

import com.liyz.boot3.common.util.annotation.Desensitization;
import com.liyz.boot3.common.util.constant.DesensitizationType;
import com.liyz.boot3.common.util.service.DesensitizeService;
import org.apache.commons.lang3.StringUtils;

/**
 * 注释:自定义脱敏
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2022/4/20 20:37
 */
public class SelfDefinitionDesensitizeServiceImpl implements DesensitizeService {

    @Override
    public String desensitize(String value, Desensitization annotation) {
        if (StringUtils.isNotBlank(value)) {
            int length = value.length();
            int beginIndex = annotation.beginIndex();
            int endIndex = annotation.endIndex();
            if (beginIndex >= 0 && beginIndex < endIndex && endIndex <= length) {
                String begin = StringUtils.left(value, beginIndex);
                String end = StringUtils.right(value, length - endIndex);
                end = StringUtils.leftPad(end, length - beginIndex, "*");
                value = begin + end;
            } else if (beginIndex == -1 && endIndex == -1) {
                value = StringUtils.leftPad(StringUtils.EMPTY, length, "*");
            } else if (beginIndex == -1 && endIndex >= 0 && endIndex <= length) {
                value = StringUtils.rightPad(StringUtils.left(value, length - endIndex), length, "*");
            } else if (endIndex == -1 && beginIndex >= 0 && beginIndex <= length) {
                value = StringUtils.leftPad(StringUtils.right(value, length - beginIndex), length, "*");
            }
        }
        return value;
    }

    @Override
    public DesensitizationType getType() {
        return DesensitizationType.SELF_DEFINITION;
    }
}
