package com.liyz.boot3.common.search.toolkit;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 13:36
 */
public interface LambdaMeta {

    /**
     * 获取 lambda 表达式实现方法的名称
     *
     * @return lambda 表达式对应的实现方法名称
     */
    String getImplMethodName();

    /**
     * 实例化该方法的类
     *
     * @return 返回对应的类名称
     */
    Class<?> getInstantiatedClass();
}
