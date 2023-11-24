package com.liyz.boot3.common.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 15:16
 */
@UtilityClass
public class PatternUtil {

    /**
     * 邮箱正则表达式
     */
    public static final String EMAIL_REG = "^([a-zA-Z0-9_\\-\\.\\+]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    /**
     * 手机正则表达式
     */
    public static final String PHONE_REG = "^1(3|4|5|7|8|9)\\d{9}$";

    /**
     * 密码强度校验
     */
    public static final String PASSWORD_STRONG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";

    /**
     * 匹配手机
     *
     * @param mobile
     * @return
     */
    public static boolean matchMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        Pattern p = Pattern.compile(PHONE_REG);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 匹配邮箱
     *
     * @param email
     * @return
     */
    public static boolean matchEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        Pattern p = Pattern.compile(EMAIL_REG);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 校验地址是否是邮件或者手机号码格式，如果不是，则抛出异常
     *
     * @param address
     * @return 1：手机号码；2：邮件；-1：无法判断
     */
    public static int checkMobileEmail(String address) {
        int type = -1;
        if (matchMobile(address)) {
            type = 1;
        } else if (matchEmail(address)){
            type = 2;
        }
        return type;
    }
}
