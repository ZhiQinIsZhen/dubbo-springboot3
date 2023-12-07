package com.liyz.boot3.common.lock.util;

import com.liyz.boot3.common.lock.exception.LockException;
import com.liyz.boot3.common.lock.exception.LockExceptionCodeEnum;
import com.liyz.boot3.common.lock.service.LockInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/10/12 17:17
 */
@Slf4j
public class RedisLockUtil {

    private static RedissonClient REDISSON_CLIENT;

    public static void setRedissonClient(RedissonClient redissonClient) {
        REDISSON_CLIENT = redissonClient;
    }

    /**
     * 非公平加锁
     *
     * @param key 锁的key
     * @param func 方法
     * @return 返回结果
     * @param <T> 泛型
     */
    public static <T> T lock(final String key, LockInterface func) {
        return lock(key, false, func);
    }

    /**
     * 非公平加锁
     *
     * @param key 锁的key
     * @param heldThrow 未持有是否抛异常
     * @param func 方法
     * @return 返回结果
     * @param <T> 泛型
     */
    public static <T> T lock(final String key, boolean heldThrow, LockInterface func) {
        RLock rLock = REDISSON_CLIENT.getLock(key);
        try {
            rLock.lock();
            return (T) func.callBack();
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            } else if (heldThrow) {
                throw new LockException(LockExceptionCodeEnum.NOT_HELD_LOCK);
            }
        }
    }

    /**
     * 非公平加锁
     *
     * @param key 锁的key
     * @param leaseTime 加锁时间
     * @param unit 单位
     * @param heldThrow 未持有是否抛异常
     * @param func 方法
     * @return 返回结果
     * @param <T> 泛型
     */
    public static <T> T lock(final String key, long leaseTime, TimeUnit unit, boolean heldThrow, LockInterface func) {
        RLock rLock = REDISSON_CLIENT.getLock(key);
        try {
            rLock.lock(leaseTime, unit);
            return (T) func.callBack();
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            } else if (heldThrow) {
                throw new LockException(LockExceptionCodeEnum.NOT_HELD_LOCK);
            }
        }
    }

    /**
     * 非公平尝试加锁
     *
     * @param key 锁的key
     * @param waitTime 等待时间
     * @param leaseTime 加锁时间
     * @param unit 单位
     * @param heldThrow 未持有是否抛异常
     * @param func 方法
     * @return 返回结果
     * @param <T> 泛型
     */
    public static <T> Pair<Boolean, T> tryLock(final String key,long waitTime, long leaseTime, TimeUnit unit, boolean heldThrow, LockInterface func) {
        RLock rLock = REDISSON_CLIENT.getLock(key);
        try {
            if (rLock.tryLock(waitTime, leaseTime, unit)) {
                return Pair.of(Boolean.TRUE, (T) func.callBack());
            }
            return Pair.of(Boolean.FALSE, null);
        } catch (InterruptedException e) {
            log.warn("key:[{}] interrupted", key, e);
            return Pair.of(Boolean.FALSE, null);
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            } else if (heldThrow) {
                throw new LockException(LockExceptionCodeEnum.NOT_HELD_LOCK);
            }
        }
    }

    /**
     * 公平加锁
     *
     * @param key 锁的key
     * @param func 方法
     * @return 返回结果
     * @param <T> 泛型
     */
    public static <T> T fairLock(final String key, LockInterface func) {
        return fairLock(key, false, func);
    }

    /**
     * 公平加锁
     *
     * @param key 锁的key
     * @param func 方法
     * @return 返回结果
     * @param <T> 泛型
     */
    public static <T> T fairLock(final String key, boolean heldThrow, LockInterface func) {
        RLock rLock = REDISSON_CLIENT.getFairLock(key);
        try {
            rLock.lock();
            return (T) func.callBack();
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            } else if (heldThrow) {
                throw new LockException(LockExceptionCodeEnum.NOT_HELD_LOCK);
            }
        }
    }

    /**
     * 公平加锁
     *
     * @param key 锁的key
     * @param leaseTime 加锁时间
     * @param unit 单位
     * @param heldThrow 未持有是否抛异常
     * @param func 方法
     * @return 返回结果
     * @param <T> 泛型
     */
    public static <T> T fairLock(final String key, long leaseTime, TimeUnit unit, boolean heldThrow, LockInterface func) {
        RLock rLock = REDISSON_CLIENT.getFairLock(key);
        try {
            rLock.lock(leaseTime, unit);
            return (T) func.callBack();
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            } else if (heldThrow) {
                throw new LockException(LockExceptionCodeEnum.NOT_HELD_LOCK);
            }
        }
    }

    /**
     * 联锁
     *
     * @param func 方法
     * @param keys 锁的keys
     * @return 返回结果
     * @param <T> 泛型
     */
    public static <T> T multiLock(LockInterface func, final String... keys) {
        return multiLock(func, 1, TimeUnit.MINUTES, keys);
    }

    /**
     * 联锁
     *
     * @param func 方法
     * @param leaseTime 加锁时间
     * @param unit 单位
     * @param keys 锁的keys
     * @return 返回结果
     * @param <T> 泛型
     */
    public static <T> T multiLock(LockInterface func, long leaseTime, TimeUnit unit, final String... keys) {
        RedissonMultiLock multiLock = new RedissonMultiLock(Arrays.stream(keys).map(key -> REDISSON_CLIENT.getLock(key)).toArray(RLock[]::new));
        try {
            multiLock.lock(leaseTime, unit);
            return (T) func.callBack();
        } finally {
            multiLock.unlock();
        }
    }
}
