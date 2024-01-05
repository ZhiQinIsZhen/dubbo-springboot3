package com.liyz.boot3.common.search.toolkit;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Desc:支持序列化的 Function
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 14:08
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
