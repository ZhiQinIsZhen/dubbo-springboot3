package com.liyz.boot3.common.es.mapper;

import lombok.AllArgsConstructor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/26 10:18
 */
@AllArgsConstructor
public enum EsCommandType {
    UNKNOWN,
    SAVE,
    SELECT,
    DELETE,
    ;
}
