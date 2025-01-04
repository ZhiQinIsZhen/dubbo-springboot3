package com.liyz.boot3.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * DESC:虚拟线程工具类
 *
 * @author lyz
 * @version 1.0.0
 * @date 2025/1/4 16:00
 */
@Slf4j
@UtilityClass
public class VirtualThreadUtil {

    /**
     * 提交任务
     *
     * @param task 任务
     */
    public static void submit(Runnable task) {
        try(ExecutorService es = Executors.newVirtualThreadPerTaskExecutor()) {
            es.submit(task);
        }
    }

    /**
     * 提交任务
     *
     * @param task 任务
     * @return 结果
     * @param <T> 泛型
     */
    public static <T> T submit(Callable<T> task) {
        try(ExecutorService es = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<T> submit = es.submit(task);
            return submit.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
