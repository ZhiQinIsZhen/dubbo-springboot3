package com.liyz.boot3.common.search.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/11 15:46
 */
@Getter
@AllArgsConstructor
public enum EsSortField {
    COUNT("_count"),
    KEY("_key"),
    ;

    private final String sortField;
}
