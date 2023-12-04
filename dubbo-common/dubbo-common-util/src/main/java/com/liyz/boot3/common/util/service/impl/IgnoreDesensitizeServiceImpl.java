package com.liyz.boot3.common.util.service.impl;

import com.liyz.boot3.common.util.annotation.Desensitization;
import com.liyz.boot3.common.util.constant.DesensitizationType;
import com.liyz.boot3.common.util.service.DesensitizeService;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 注释:忽略此字符
 * 注：如果此字符为null，则返回null；如果不为null，则返回一个长度为0的空字符
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2022/4/20 20:32
 */
public class IgnoreDesensitizeServiceImpl implements DesensitizeService {

    @Override
    public String desensitize(String value, Desensitization annotation) {
        return Objects.nonNull(value) ? StringUtils.EMPTY : null;
    }

    @Override
    public DesensitizationType getType() {
        return DesensitizationType.IGNORE;
    }
}
