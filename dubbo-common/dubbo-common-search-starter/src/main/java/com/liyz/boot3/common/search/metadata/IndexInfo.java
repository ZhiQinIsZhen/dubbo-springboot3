package com.liyz.boot3.common.search.metadata;

import com.liyz.boot3.common.search.toolkit.Reflector;
import com.liyz.boot3.common.search.util.IndexInfoUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 15:23
 */
@Getter
@Setter
public class IndexInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -909393029036193456L;

    /**
     * 实体类型
     */
    private Class<?> entityType;

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 表主键ID 字段名
     */
    private String keyColumn;
    /**
     * 表主键ID 属性名
     */
    private String keyProperty;
    /**
     * 表主键ID 属性类型
     */
    private Class<?> keyType;

    @Getter
    private Reflector reflector;

    /**
     * 表字段信息列表
     */
    private List<IndexFieldInfo> fieldList;

    public IndexInfo(Class<?> entityType) {
        this.entityType = entityType;
        this.reflector = IndexInfoUtil.getReflector(entityType);
    }

    /**
     * 是否有主键
     *
     * @return 是否有
     */
    public boolean havePK() {
        return StringUtils.isNotBlank(keyColumn);
    }
}
