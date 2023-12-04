package com.liyz.boot3.common.util.service.impl;

import com.liyz.boot3.common.util.annotation.Desensitization;
import com.liyz.boot3.common.util.constant.DesensitizationType;
import com.liyz.boot3.common.util.service.DesensitizeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 注释:邮箱脱敏
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2022/4/20 20:30
 */
@Component
public class EmailDesensitizeServiceImpl implements DesensitizeService {

    private static final String Email_Regex = "(\\w+)\\w{%d}@(\\w+)";

    @Override
    public String desensitize(String value, Desensitization annotation) {
        if (StringUtils.isNotBlank(value)) {
            int index = value.indexOf("@");
            if (index > 0) {
                index = (int) (index * 0.6);
                value = value.replaceAll(String.format(Email_Regex, index), "$1****$2");
            }
        }
        return value;
    }

    @Override
    public DesensitizationType getType() {
        return DesensitizationType.EMAIL;
    }
}
