package com.liyz.boot3.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/5/23 13:22
 */
@Slf4j
@UtilityClass
public class TraceIdUtil {

    private static final String traceIdMethod = "getGlobalTraceId";
    private static Class<?> contextManagerClass;
    private static volatile Object contextManager;

    /**
     * 获取链路ID
     *
     * @return traceId
     */
    public static String getTraceId() {
        if (getContextManager() == null) {
            return StringUtils.EMPTY;
        } else {
            try {
                return (String) contextManagerClass.getMethod(traceIdMethod).invoke(contextManager);
            } catch (Exception e) {
                return "N/A";
            }
        }
    }

    /**
     * 获取实例对象
     *
     * @return 实例对象
     */
    private static Object getContextManager() {
        if (contextManager != null) {
            return contextManager;
        }
        synchronized (traceIdMethod) {
            if (contextManager != null) {
                return contextManager;
            }
            try {
                contextManagerClass = Class.forName("org.apache.skywalking.apm.agent.core.context.ContextManager");
                contextManager = contextManagerClass.newInstance();
            } catch (Exception e) {
                log.error("加载skywalking的traceId工具类失败!!!!!!!!!!!!!!!!");
            }
            return contextManager;
        }
    }
}
