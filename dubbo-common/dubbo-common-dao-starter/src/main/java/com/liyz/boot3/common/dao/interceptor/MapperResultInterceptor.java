package com.liyz.boot3.common.dao.interceptor;

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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (Objects.isNull(result)){
            return null;
        }
        if (result instanceof List resultList) {
            if (!CollectionUtils.isEmpty(resultList)) {
                Set<Field> fields = this.getDesensitizeFields(resultList.getFirst());
                if (CollectionUtils.isEmpty(fields)) {
                    return result;
                }
                for (Object obj : resultList) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Desensitization desensitization = field.getAnnotation(Desensitization.class);
                        Object value = field.get(obj);
                        if (Objects.nonNull(value) && !value.toString().trim().isEmpty()) {
                            field.set(
                                    obj,
                                    DesensitizeUtil.getService(desensitization.value()).desensitize(value.toString(), desensitization)
                            );
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取该返回类型需要脱敏的字段
     *
     * @param target
     * @return
     */
    private Set<Field> getDesensitizeFields(Object target) {
        Set<Field> fieldSet = new HashSet<>();
        Class<?> cls = target.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class && field.isAnnotationPresent(Desensitization.class)) {
                fieldSet.add(field);
            }
        }
        return fieldSet;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }
}
