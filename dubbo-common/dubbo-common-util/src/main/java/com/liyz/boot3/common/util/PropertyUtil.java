package com.liyz.boot3.common.util;

import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.management.ReflectionException;
import java.util.Locale;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 14:14
 */
@Slf4j
@UtilityClass
public class PropertyUtil {

    public static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        } else {
            log.error("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            throw new RemoteServiceException(CommonExceptionCodeEnum.PARSING_PROPERTY_NAME_ERROR);
        }

        if (name.length() == 1 || name.length() > 1 && !Character.isUpperCase(name.charAt(1))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    public static boolean isGetter(String name) {
        return name.startsWith("get") && name.length() > 3 || name.startsWith("is") && name.length() > 2;
    }

    public static boolean isSetter(String name) {
        return name.startsWith("set") && name.length() > 3;
    }
}
