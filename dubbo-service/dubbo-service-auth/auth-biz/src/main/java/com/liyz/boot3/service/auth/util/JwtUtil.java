package com.liyz.boot3.service.auth.util;

import cn.hutool.jwt.JWT;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/30 16:11
 */
@UtilityClass
public class JwtUtil {

    /**
     * 构建jwt
     *
     * @return JwtBuilder
     */
    public static JwtBuilder builder() {
        return Jwts.builder();
    }

    /**
     * 构建jwt解析
     *
     * @return JwtParserBuilder
     */
    public static JwtParserBuilder parser() {
        return Jwts.parser();
    }

    /**
     * 解码
     *
     * @param jwt token
     * @param tClass 转化的实体类
     * @return 实体类
     * @param <T> 实体类泛型
     */
    public static <T> T decode(final String jwt, Class<T> tClass) {
        return JWT.of(jwt).getPayloads().toBean(tClass);
    }
}
