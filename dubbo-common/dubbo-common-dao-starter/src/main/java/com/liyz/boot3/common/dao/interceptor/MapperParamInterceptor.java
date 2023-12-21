package com.liyz.boot3.common.dao.interceptor;

import com.liyz.boot3.common.util.DesensitizeUtil;
import com.liyz.boot3.common.util.annotation.Desensitization;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Objects;

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

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
        parameterField.setAccessible(true);
        Object parameterObject = parameterField.get(parameterHandler);
        if (Objects.nonNull(parameterObject)) {
            Class<?> cls = parameterObject.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == String.class && field.isAnnotationPresent(Desensitization.class)) {
                    field.setAccessible(true);
                    Desensitization desensitization = field.getAnnotation(Desensitization.class);
                    Object value = field.get(parameterObject);
                    if (Objects.nonNull(value) && !value.toString().trim().isEmpty()) {
                        field.set(
                                parameterObject,
                                DesensitizeUtil.getService(desensitization.value()).desensitize(value.toString(), desensitization)
                        );
                    }
                }
            }

        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }
}
