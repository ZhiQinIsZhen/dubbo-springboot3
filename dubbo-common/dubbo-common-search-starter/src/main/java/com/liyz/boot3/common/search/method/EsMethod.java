package com.liyz.boot3.common.search.method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 21:14
 */
@Getter
@AllArgsConstructor
public enum EsMethod {
    SELECT_BY_ID("selectById", "根据ID 查询一条数据"),
    SELECT_BATCH_BY_IDS("selectBatchIds", "根据ID集合，批量查询数据"),
    ;

    private final String method;
    private final String desc;

    public static EsMethod getByMethod(String method) {
        if (!StringUtils.hasText(method)) {
            return null;
        }
        for (EsMethod esMethod : EsMethod.values()) {
            if (method.equals(esMethod.getMethod())) {
                return esMethod;
            }
        }
        return null;
    }
}
