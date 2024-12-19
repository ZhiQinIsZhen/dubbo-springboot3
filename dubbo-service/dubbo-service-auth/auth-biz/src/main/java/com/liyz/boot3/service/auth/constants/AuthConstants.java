package com.liyz.boot3.service.auth.constants;

import com.google.common.base.Joiner;
import com.liyz.boot3.common.service.constant.CommonServiceConstant;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 9:28
 */
@UtilityClass
public class AuthConstants {

    /**
     * redis前缀
     */
    public final String REDIS_PREFIX = "auth";

    public final String LOGIN_KEY = "loginKey";

    /**
     * 获取redis的key
     *
     * @param keys
     * @return
     */
    public static String getRedisKey(String... keys) {
        return Joiner.on(CommonServiceConstant.DEFAULT_JOINER).join(REDIS_PREFIX, LOGIN_KEY, keys);
    }
}
