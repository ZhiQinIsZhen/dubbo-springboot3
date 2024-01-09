package com.liyz.boot3.common.search.Query;

import lombok.AllArgsConstructor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/9 9:49
 */
@AllArgsConstructor
public enum EsKeyword {
    TERM,
    TERMS,
    MUST,
    NOT_MUST,
    SHOULD,
    ;
}
