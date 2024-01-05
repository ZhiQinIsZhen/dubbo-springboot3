package com.liyz.boot3.common.search.toolkit;

import com.liyz.boot3.common.util.ClassUtils;

import java.lang.invoke.SerializedLambda;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 13:41
 */
public class ReflectLambdaMeta implements LambdaMeta{

    private final SerializedLambda lambda;
    private final ClassLoader classLoader;

    public ReflectLambdaMeta(SerializedLambda lambda, ClassLoader classLoader) {
        this.lambda = lambda;
        this.classLoader = classLoader;
    }

    @Override
    public String getImplMethodName() {
        return lambda.getImplMethodName();
    }

    @Override
    public Class<?> getInstantiatedClass() {
        String instantiatedMethodType = lambda.getInstantiatedMethodType();
        String instantiatedType = instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(";")).replace("/", ".");
        return ClassUtils.toClassConfident(instantiatedType, this.classLoader);
    }

    @Override
    public String toString() {
        return getInstantiatedClass().getSimpleName() + "::" + getImplMethodName();
    }
}
