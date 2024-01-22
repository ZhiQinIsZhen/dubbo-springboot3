package com.liyz.boot3.common.search.util;

import com.liyz.boot3.common.search.metadata.IndexInfo;
import com.liyz.boot3.common.search.toolkit.*;
import lombok.experimental.UtilityClass;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Locale.ENGLISH;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 13:35
 */
@UtilityClass
public class LambdaUtils {

    /**
     * 字段映射
     */
    private static final Map<String, Map<String, ColumnCache>> COLUMN_CACHE_MAP = new ConcurrentHashMap<>();

    public static <T> LambdaMeta extract(SFunction<T, ?> func) {
        // 1. IDEA 调试模式下 lambda 表达式是一个代理
        if (func instanceof Proxy) {
            return new IdeaProxyLambdaMeta((Proxy) func);
        }
        // 2. 反射读取
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return new ReflectLambdaMeta((SerializedLambda) method.invoke(func), func.getClass().getClassLoader());
        } catch (Throwable e) {
            // 3. 反射失败使用序列化的方式读取
            return new ShadowLambdaMeta(com.liyz.boot3.common.search.toolkit.SerializedLambda.extract(func));
        }
    }

    /**
     * 获取实体对应字段 MAP
     *
     * @param clazz 实体类
     * @return 缓存 map
     */
    public static Map<String, ColumnCache> getColumnMap(Class<?> clazz) {
        return COLUMN_CACHE_MAP.computeIfAbsent(clazz.getName(), key -> {
            IndexInfo info = IndexInfoUtil.getIndexInfo(clazz);
            return info == null ? null : createColumnCacheMap(info);
        });
    }

    /**
     * 缓存实体字段 MAP 信息
     *
     * @param info 表信息
     * @return 缓存 map
     */
    private static Map<String, ColumnCache> createColumnCacheMap(IndexInfo info) {
        Map<String, ColumnCache> map;

        if (info.havePK()) {
            map = new HashMap<>(info.getFieldList().size() + 1);
            map.put(formatKey(info.getKeyProperty()), new ColumnCache(info.getKeyColumn(), info.getKeyColumn()));
        } else {
            map = new HashMap<>(info.getFieldList().size());
        }

        info.getFieldList().forEach(i ->
                map.put(formatKey(i.getProperty()), new ColumnCache(i.getProperty(), i.getColumn()))
        );
        return map;
    }

    /**
     * 格式化 key 将传入的 key 变更为大写格式
     *
     * <pre>
     *     Assert.assertEquals("USERID", formatKey("userId"))
     * </pre>
     *
     * @param key key
     * @return 大写的 key
     */
    public static String formatKey(String key) {
        return key.toUpperCase(ENGLISH);
    }
}
