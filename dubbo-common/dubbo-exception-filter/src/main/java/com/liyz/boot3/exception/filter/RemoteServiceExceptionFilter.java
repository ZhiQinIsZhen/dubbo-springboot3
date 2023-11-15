package com.liyz.boot3.exception.filter;

import com.liyz.boot3.common.remote.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.rpc.support.RpcUtils;

import java.lang.reflect.Method;

import static org.apache.dubbo.common.constants.LoggerCodeConstants.CONFIG_FILTER_VALIDATION_EXCEPTION;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/15 9:43
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class RemoteServiceExceptionFilter implements Filter, Filter.Listener{

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        if (appResponse.hasException() && GenericService.class != invoker.getInterface()) {
            try {
                Throwable exception = appResponse.getException();
                //业务异常直接返回
                if (exception instanceof RemoteServiceException) {
                    return;
                }
                // directly throw if it's checked exception
                if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
                    return;
                }
                // directly throw if the exception appears in the signature
                try {
                    Method method = invoker.getInterface().getMethod(RpcUtils.getMethodName(invocation), invocation.getParameterTypes());
                    Class<?>[] exceptionClasses = method.getExceptionTypes();
                    for (Class<?> exceptionClass : exceptionClasses) {
                        if (exception.getClass().equals(exceptionClass)) {
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    return;
                }

                // for the exception not found in method's signature, print ERROR message in server's log.
                log.error(CONFIG_FILTER_VALIDATION_EXCEPTION, "", "",
                        "Got unchecked and undeclared exception which called by " + RpcContext.getServiceContext().getRemoteHost() +
                                ". service: " + invoker.getInterface().getName() + ", method: " + RpcUtils.getMethodName(invocation) +
                                ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(), exception);

                // directly throw if exception class and interface class are in the same jar file.
                String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                    return;
                }
                // directly throw if it's JDK exception
                String className = exception.getClass().getName();
                if (className.startsWith("java.") || className.startsWith("javax.") || className.startsWith("jakarta.")) {
                    return;
                }
                // directly throw if it's dubbo exception
                if (exception instanceof RpcException) {
                    return;
                }

                // otherwise, wrap with RuntimeException and throw back to the client
                appResponse.setException(new RuntimeException(StringUtils.toString(exception)));
            } catch (Throwable e) {
                log.warn(CONFIG_FILTER_VALIDATION_EXCEPTION, "", "",
                        "Fail to ExceptionFilter when called by " + RpcContext.getServiceContext().getRemoteHost() +
                                ". service: " + invoker.getInterface().getName() + ", method: " + RpcUtils.getMethodName(invocation) +
                                ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void onError(Throwable e, Invoker<?> invoker, Invocation invocation) {
        log.error(CONFIG_FILTER_VALIDATION_EXCEPTION, "", "",
                "Got unchecked and undeclared exception which called by " + RpcContext.getServiceContext().getRemoteHost() +
                        ". service: " + invoker.getInterface().getName() + ", method: " + RpcUtils.getMethodName(invocation) +
                        ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
    }
}
