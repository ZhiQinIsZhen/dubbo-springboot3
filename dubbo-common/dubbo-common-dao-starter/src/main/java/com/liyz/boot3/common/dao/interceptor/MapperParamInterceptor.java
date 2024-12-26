package com.liyz.boot3.common.dao.interceptor;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.util.ReflectUtil;
import com.liyz.boot3.common.util.DesensitizeUtil;
import com.liyz.boot3.common.util.annotation.Desensitization;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
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
        type = ParameterHandler.class,
        method = "setParameters",
        args={PreparedStatement.class}
)})
public class MapperParamInterceptor implements Interceptor {

    private final ConcurrentHashMap<Class<?>, List<Field>> containMap = new ConcurrentHashMap<>();
    private final ConcurrentHashSet<Class<?>> nonContainSet = new ConcurrentHashSet<>();

    /**
     * todo 这里没有解决mybatis-plus的参数包装（wrapper），如果有需要可以自行扩展或者重新设计
     * todo 这里也没有解决根据某个角色或者权限去判断，动态的脱敏，可以自行增加上下文信息，进行设计
     *
     * @param invocation 调用信息
     * @return 结果
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        Object parameterObject = parameterHandler.getParameterObject();
        if (Objects.nonNull(parameterObject)) {
            if (parameterObject instanceof List<?> parameterList) {
                if (!CollectionUtils.isEmpty(parameterList)) {
                    parameterList.forEach(this::doIntercept);
                }
            } else {
                this.doIntercept(parameterObject);
            }
        }
        return invocation.proceed();
    }

    /**
     * 数据处理
     *
     * @param parameter 入参
     */
    private void doIntercept(Object parameter) {
        Class<?> cls = parameter.getClass();
        if (nonContainSet.contains(cls)) {
            return;
        }
        List<Field> fields = containMap.get(cls);
        if (CollectionUtils.isEmpty(fields)) {
            fields = Arrays.stream(ReflectUtil.getFields(cls))
                    .filter(f -> f.isAnnotationPresent(Desensitization.class) && f.getAnnotation(Desensitization.class).dbParam())
                    .toList();
            if (CollectionUtils.isEmpty(fields)) {
                nonContainSet.add(cls);
                return;
            } else {
                containMap.putIfAbsent(cls, fields);
            }
        }
        fields.forEach(f -> {
            Desensitization desensitization = f.getAnnotation(Desensitization.class);
            Object value = ReflectUtil.getFieldValue(parameter, f);
            if (Objects.nonNull(value) && value instanceof String && !value.toString().trim().isEmpty()) {
                ReflectUtil.setFieldValue(parameter, f, DesensitizeUtil.getService(desensitization.value()).desensitize(value.toString(), desensitization));
            }
        });
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }
}
