package com.liyz.boot3.common.dao.interceptor;


import cn.hutool.core.util.ReflectUtil;
import com.liyz.boot3.common.util.DesensitizeUtil;
import com.liyz.boot3.common.util.annotation.Desensitization;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/21 10:52
 */
@Intercepts({@Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args={Statement.class}
)})
public class MapperResultInterceptor implements Interceptor {

    private final static Object VALUE = new Object();

    private final ConcurrentHashMap<Class<?>, List<Field>> containMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<?>, Object> nonContainMap = new ConcurrentHashMap<>();

    /**
     * todo 这里只是提供了一个思路，没有解决多层套用关系（只对第一层字段进行判断）
     * todo 这里也没有解决根据某个角色或者权限去判断，动态的脱敏，可以自行增加上下文信息，进行设计
     *
     * @param invocation 调用信息
     * @return 结果
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (Objects.isNull(result)){
            return null;
        }
        if (result instanceof List<?> resultList) {
            if (CollectionUtils.isEmpty(resultList)) {
                return result;
            }
            Object first = resultList.getFirst();
            if (nonContainMap.contains(first.getClass())) {
                return result;
            }
            List<Field> fields = containMap.get(first.getClass());
            if (CollectionUtils.isEmpty(fields)) {
                fields = Arrays.stream(ReflectUtil.getFields(first.getClass()))
                        .filter(f -> f.isAnnotationPresent(Desensitization.class) && f.getAnnotation(Desensitization.class).dbResult())
                        .toList();
                if (CollectionUtils.isEmpty(fields)) {
                    nonContainMap.put(first.getClass(), VALUE);
                    return result;
                } else {
                    containMap.putIfAbsent(first.getClass(), fields);
                }
            }
            for (Object obj : resultList) {
                fields.forEach(f -> {
                    Desensitization desensitization = f.getAnnotation(Desensitization.class);
                    Object value = ReflectUtil.getFieldValue(obj, f);
                    if (Objects.nonNull(value) && value instanceof String && !value.toString().trim().isEmpty()) {
                        ReflectUtil.setFieldValue(obj, f, DesensitizeUtil.getService(desensitization.value()).desensitize(value.toString(), desensitization));
                    }
                });
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }
}
