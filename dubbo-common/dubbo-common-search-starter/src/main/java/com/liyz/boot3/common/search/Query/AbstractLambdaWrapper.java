package com.liyz.boot3.common.search.Query;

import com.liyz.boot3.common.search.toolkit.ColumnCache;
import com.liyz.boot3.common.search.toolkit.LambdaMeta;
import com.liyz.boot3.common.search.toolkit.SFunction;
import com.liyz.boot3.common.search.util.LambdaUtils;
import com.liyz.boot3.common.util.PropertyUtil;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 11:08
 */
public class AbstractLambdaWrapper<T, Children extends AbstractLambdaWrapper<T, Children>> extends
        AbstractWrapper<T, SFunction<T, ?>, Children> {

    private Map<String, ColumnCache> columnMap = null;
    private boolean initColumnMap = false;

    protected final List<String> columnsToIncludes(boolean onlyColumn, List<SFunction<T, ?>> columns) {
        return columns.stream().map(item -> columnToString(onlyColumn, item)).collect(Collectors.toList());
    }

    @Override
    protected String columnToString(SFunction<T, ?> column) {
        return columnToString(false, column);
    }

    protected String columnToString(boolean onlyColumn, SFunction<T, ?> column) {
         ColumnCache cache = getColumnCache(column);
         return onlyColumn ? cache.getColumn() : cache.getColumnSelect();
    }

    protected ColumnCache getColumnCache(SFunction<T, ?> column) {
        LambdaMeta meta = LambdaUtils.extract(column);
        String fieldName = PropertyUtil.methodToProperty(meta.getImplMethodName());
        Class<?> instantiatedClass = meta.getInstantiatedClass();
        tryInitCache(instantiatedClass);
        return getColumnCache(fieldName, instantiatedClass);
    }

    private void tryInitCache(Class<?> lambdaClass) {
        if (!initColumnMap) {
            final Class<T> entityClass = getEntityClass();
            if (entityClass != null) {
                lambdaClass = entityClass;
            }
            columnMap = LambdaUtils.getColumnMap(lambdaClass);
            Assert.notNull(columnMap, String.format("can not find lambda cache for this entity [%s]", lambdaClass.getName()));
            initColumnMap = true;
        }
    }

    private ColumnCache getColumnCache(String fieldName, Class<?> lambdaClass) {
        ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(fieldName));
        Assert.notNull(columnCache, String.format("can not find lambda cache for this property [%s] of entity [%s]",
                fieldName, lambdaClass.getName()));
        return columnCache;
    }

    /**
     * 构建List
     *
     * @since 3.5.4
     */
    @SafeVarargs
    protected static <T> List<T> toList(T... t) {
        if (t != null) {
            return Arrays.asList(t);
        }
        return Collections.emptyList();
    }
}
