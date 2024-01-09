package com.liyz.boot3.common.search.util;

import cn.hutool.core.util.ReflectUtil;
import com.liyz.boot3.common.search.metadata.IndexFieldInfo;
import com.liyz.boot3.common.search.metadata.IndexInfo;
import com.liyz.boot3.common.search.toolkit.Reflector;
import com.liyz.boot3.common.util.ClassUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 15:21
 */
@Slf4j
@UtilityClass
public class IndexInfoUtil {

    /**
     * 储存反射类表信息
     */
    private static final Map<Class<?>, IndexInfo> INDEX_INFO_CACHE = new ConcurrentHashMap<>();

    /**
     * 储存表名对应的反射类表信息
     */
    private static final Map<String, IndexInfo> INDEX_NAME_INFO_CACHE = new ConcurrentHashMap<>();

    private final ConcurrentMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<>();

    /**
     * 默认表主键名称
     */
    private static final String DEFAULT_ID_NAME = "id";

    public static Reflector getReflector(Class<?> clazz) {
        Reflector reflector = reflectorMap.get(clazz);
        if (reflector == null) {
            reflector = reflectorMap.computeIfAbsent(clazz, k -> new Reflector(clazz));
        }
        return reflector;
    }

    /**
     * <p>
     * 获取实体映射表信息
     * </p>
     *
     * @param clazz 反射实体类
     * @return 数据库表反射信息
     */
    public static IndexInfo getIndexInfo(Class<?> clazz) {
        if (clazz == null || clazz.isPrimitive() || clazz.isInterface() || !clazz.isAnnotationPresent(Document.class)) {
            return null;
        }
        Class<?> targetClass = ClassUtils.getUserClass(clazz);
        IndexInfo indexInfo = INDEX_INFO_CACHE.get(targetClass);
        if (null != indexInfo) {
            return indexInfo;
        }
        //尝试获取父类缓存
        Class<?> currentClass = clazz;
        while (null == indexInfo && Object.class != currentClass) {
            currentClass = currentClass.getSuperclass();
            indexInfo = INDEX_INFO_CACHE.get(ClassUtils.getUserClass(currentClass));
        }
        if (indexInfo == null) {
            indexInfo = initIndexInfo(clazz);
            INDEX_INFO_CACHE.put(targetClass, indexInfo);
            INDEX_NAME_INFO_CACHE.put(indexInfo.getIndexName(), indexInfo);
        }
        return indexInfo;
    }

    private static synchronized IndexInfo initIndexInfo(Class<?> entityType) {
        IndexInfo indexInfo = new IndexInfo(entityType);
        Document document = entityType.getAnnotation(Document.class);
        indexInfo.setIndexName(document.indexName());
        List<Field> fields = Arrays.stream(ReflectUtil.getFields(entityType))
                .filter(f -> indexInfo.getReflector().isValidPropertyName(f.getName()))
                .toList();
        Field field = fields.stream().filter(f -> f.isAnnotationPresent(Id.class)).findAny().orElse(null);
        if (field != null) {
            indexInfo.setKeyProperty(field.getName());
            indexInfo.setKeyType(field.getType());
            indexInfo.setKeyColumn(camelToUnderline(field.getName()));
        }
        List<IndexFieldInfo> fieldList = new ArrayList<>();
        for (Field item : fields) {
            org.springframework.data.elasticsearch.annotations.Field annotationField = item.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
            if (annotationField != null) {
                if (annotationField.enabled()) {
                    fieldList.add(new IndexFieldInfo(indexInfo, item, indexInfo.getReflector()));
                }
            } else {
                fieldList.add(new IndexFieldInfo(indexInfo, item, indexInfo.getReflector()));
            }
        }
        indexInfo.setFieldList(fieldList);
        return indexInfo;
    }

    public static String camelToUnderline(String param) {
        if (StringUtils.isBlank(param)) {
            return StringUtils.EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append('_');
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * <p>
     * 根据表名获取实体映射表信息
     * </p>
     *
     * @param tableName 表名
     * @return 数据库表反射信息
     */
    public static IndexInfo getIndexInfo(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        return INDEX_NAME_INFO_CACHE.get(tableName);
    }
}
