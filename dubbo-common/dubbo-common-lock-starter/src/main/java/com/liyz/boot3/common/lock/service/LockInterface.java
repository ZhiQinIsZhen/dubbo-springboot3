package com.liyz.boot3.common.lock.service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/10/12 18:12
 */
@FunctionalInterface
public interface LockInterface {

    Object callBack();
}
