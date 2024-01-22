package com.liyz.boot3.common.search.metadata;

import com.liyz.boot3.common.search.toolkit.Reflector;
import com.liyz.boot3.common.search.util.IndexInfoUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 15:26
 */
@Getter
@Setter
public class IndexFieldInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 6244448969221380667L;

    /**
     * 属性
     */
    private final Field field;

    /**
     * 字段名
     */
    private final String column;

    /**
     * 属性名
     */
    private final String property;

    /**
     * 属性类型
     */
    private final Class<?> propertyType;

    /**
     * 是否是基本数据类型
     */
    private final boolean isPrimitive;

    /**
     * 是否进行 select 查询
     * <p>大字段可设置为 false 不加入 select 查询范围</p>
     */
    private boolean select = true;

    public IndexFieldInfo(IndexInfo indexInfo, Field field, Reflector reflector) {
        this.field = field;
        this.property = field.getName();
        this.propertyType = reflector.getGetterType(this.property);
        this.isPrimitive = this.propertyType.isPrimitive();
        org.springframework.data.elasticsearch.annotations.Field annotationField = field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
        if (annotationField == null || StringUtils.isBlank(annotationField.name())) {
            this.column = IndexInfoUtil.camelToUnderline(property);
        } else {
            this.column = annotationField.name();
        }
    }


}
