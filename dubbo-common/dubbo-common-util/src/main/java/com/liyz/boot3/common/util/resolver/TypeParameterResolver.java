package com.liyz.boot3.common.util.resolver;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/15 16:35
 */
public class TypeParameterResolver {
    private final Map<TypeVariable<?>, Type> map;
    private final Set<Type> distinct;

    protected TypeParameterResolver(Map<TypeVariable<?>, Type> map) {
        this.map = map;
        this.distinct = new HashSet<>();
    }

    /**
     * 获取类型上指定索引位置参数的实现信息
     *
     * @param source 类型
     * @param index  索引
     * @param type   实现类型
     * @return 返回类型实现或者 null
     */
    public static Type resolveClassIndexedParameter(Type type, Class<?> source, int index) {
        return calculateParameterValue(resolveParameterValues(type), source.getTypeParameters()[index]);
    }

    /**
     * 计算参数值
     *
     * @param map       变量 Map
     * @param parameter 参数
     * @return 返回参数值
     */
    public static Type calculateParameterValue(Map<TypeVariable<?>, Type> map, TypeVariable<?> parameter) {
        Type res = map.get(parameter);
        while (res instanceof TypeVariable<?>) {
            res = map.get(res);
        }
        return res;
    }

    /**
     * 解析指定类型下的泛型参数实现信息
     *
     * @param from 起始类型
     * @return 返回全部的泛型参数及其映射类型值
     */
    public static Map<TypeVariable<?>, Type> resolveParameterValues(Type from) {
        Map<TypeVariable<?>, Type> map = new HashMap<>();
        new TypeParameterResolver(map).visitType(from);
        return map;
    }

    /**
     * 访问类型，类型中需要关注两个：{@link Class} 和 {@link ParameterizedType}
     *
     * @param type 类型
     */
    public void visitType(Type type) {
        if (!distinct.add(type)) {
            return;
        }

        if (type instanceof Class<?>) {
            visitClass((Class<?>) type);
            return;
        }

        if (type instanceof ParameterizedType) {
            visitParameterizedType((ParameterizedType) type);
        }

    }

    /**
     * 访问类型，类型的树可以分解为父类和接口，这两个地方都要解析。
     *
     * @param c 类
     */
    private void visitClass(Class<?> c) {
        visitType(c.getGenericSuperclass());
        for (Type i : c.getGenericInterfaces()) {
            visitType(i);
        }
    }

    /**
     * 访问参数化类型，类型参数映射的主要逻辑就在这里
     *
     * @param parameterized 参数化类型
     */
    private void visitParameterizedType(ParameterizedType parameterized) {
        Type raw = parameterized.getRawType();
        visitType(raw);

        if (raw instanceof GenericDeclaration) {
            GenericDeclaration declaration = (GenericDeclaration) raw;
            TypeVariable<?>[] parameters = declaration.getTypeParameters();
            Type[] arguments = parameterized.getActualTypeArguments();
            for (int i = 0; i < parameters.length; i++) {
                TypeVariable<?> parameter = parameters[i];
                Type argument = arguments[i];
                map.put(parameter, argument);
                visitType(argument);
            }
        }
    }
}
