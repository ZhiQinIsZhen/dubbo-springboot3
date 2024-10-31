package com.liyz.boot3.api.test.util;

import com.liyz.boot3.common.remote.page.RemotePage;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 13:15
 */
@UtilityClass
public final class BeanUtil {

    /**
     * 单个对象拷贝
     *
     * @param source 原对象
     * @param targetSupplier 目标对象
     * @return 目标对象
     * @param <S> 原对象泛型
     * @param <T> 目标对象泛型
     */
    public static <S, T> T copyProperties(S source, Supplier<T> targetSupplier) {
        return copyProperties(source, targetSupplier, null);
    }

    /**
     * 单个对象拷贝
     *
     * @param source 原对象
     * @param targetSupplier 目标对象
     * @param ext 目标对象函数接口
     * @return 目标对象
     * @param <S> 原对象泛型
     * @param <T> 目标对象泛型
     */
    public static <S, T> T copyProperties(S source, Supplier<T> targetSupplier, BiConsumer<S, T> ext) {
        if (Objects.isNull(source)) {
            return null;
        }
        T target = targetSupplier.get();
        BeanUtils.copyProperties(source, target);
        if (Objects.nonNull(ext)) {
            ext.accept(source, target);
        }
        return target;
    }

    /**
     * 数组对象拷贝
     *
     * @param sources 原数组
     * @param targetSupplier 目标对象
     * @return 目标数组
     * @param <S> 原对象泛型
     * @param <T> 目标对象泛型
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> targetSupplier) {
        return copyList(sources, targetSupplier, null);
    }

    /**
     * 数组对象拷贝
     *
     * @param sources 原数组
     * @param targetSupplier 目标对象
     * @param ext 目标对象函数接口
     * @return 目标数组
     * @param <S> 原对象泛型
     * @param <T> 目标对象泛型
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> targetSupplier, BiConsumer<S, T> ext) {
        if (CollectionUtils.isEmpty(sources)) {
            return List.of();
        }
        return sources
                .stream()
                .map(source -> copyProperties(source, targetSupplier, ext))
                .collect(Collectors.toList());
    }

    /**
     * 分页对象拷贝
     *
     * @param pageSource 原对象
     * @param targetSupplier 目标对象
     * @return 目标分页
     * @param <S> 原对象泛型
     * @param <T> 目标对象泛型
     */
    public static <S, T> RemotePage<T> copyRemotePage(RemotePage<S> pageSource, Supplier<T> targetSupplier) {
        return copyRemotePage(pageSource, targetSupplier, null);
    }

    /**
     * 分页对象拷贝
     *
     * @param pageSource 原对象
     * @param targetSupplier 目标对象
     * @param ext 目标对象函数接口
     * @return 目标分页
     * @param <S> 原对象泛型
     * @param <T> 目标对象泛型
     */
    public static <S, T> RemotePage<T> copyRemotePage(RemotePage<S> pageSource, Supplier<T> targetSupplier, BiConsumer<S, T> ext) {
        if (Objects.isNull(pageSource)) {
            return RemotePage.of();
        }
        return new RemotePage<>(
                copyList(pageSource.getList(), targetSupplier, ext),
                pageSource.getTotal(),
                pageSource.getPageNum(),
                pageSource.getPageSize()
        );
    }
}
