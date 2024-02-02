package com.liyz.boot3.common.search.Query;

import lombok.AllArgsConstructor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/2/1 16:25
 */
@AllArgsConstructor
public enum EsBoolKey {
    FILTER,
    MUST,
    NOT_MUST,
    SHOULD;
}
