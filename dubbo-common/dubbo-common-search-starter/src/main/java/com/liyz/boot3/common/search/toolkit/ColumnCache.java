package com.liyz.boot3.common.search.toolkit;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 13:33
 */
@Getter
@Setter
public class ColumnCache implements Serializable {
    @Serial
    private static final long serialVersionUID = -5195044265637885402L;

    /**
     * 使用 column
     */
    private String column;

    /**
     * 查询 column
     */
    private String columnSelect;

    public ColumnCache(String column, String columnSelect) {
        this.column = column;
        this.columnSelect = columnSelect;
    }
}
