package com.liyz.boot3.common.search.query;

import cn.hutool.core.collection.CollectionUtil;
import com.liyz.boot3.common.search.toolkit.SFunction;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/3 11:29
 */
public class LambdaQueryWrapper<T> extends AbstractLambdaWrapper<T, LambdaQueryWrapper<T>>
        implements Query<LambdaQueryWrapper<T>, T, SFunction<T, ?>> {

    public LambdaQueryWrapper() {
        this((T) null);
    }

    public LambdaQueryWrapper(T entity) {
        super.setEntity(entity);
    }

    public LambdaQueryWrapper(Class<T> entityClass) {
        super.setEntityClass(entityClass);
    }

    @Override
    @SafeVarargs
    public final LambdaQueryWrapper<T> select(SFunction<T, ?>... columns) {
        return doSelect(true, toList(columns));
    }

    @Override
    @SafeVarargs
    public final LambdaQueryWrapper<T> select(boolean condition, SFunction<T, ?>... columns) {
        return doSelect(condition, toList(columns));
    }

    @Override
    public LambdaQueryWrapper<T> select(boolean condition, List<SFunction<T, ?>> columns) {
        return doSelect(condition, columns);
    }

    protected LambdaQueryWrapper<T> doSelect(boolean condition, List<SFunction<T, ?>> columns) {
        if (condition && CollectionUtil.isNotEmpty(columns)) {
            this.setIncludes(columnsToIncludes(false, columns));
        }
        return typedThis;
    }
}
