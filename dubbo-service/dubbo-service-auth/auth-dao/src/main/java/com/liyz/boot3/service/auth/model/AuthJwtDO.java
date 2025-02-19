package com.liyz.boot3.service.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liyz.boot3.common.dao.model.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:39
 */
@Getter
@Setter
@TableName("auth_jwt")
public class AuthJwtDO extends BaseDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4949126727508321753L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用名
     */
    private String clientId;

    /**
     * jwt前缀
     */
    private String jwtPrefix;

    /**
     * 签名key
     */
    private String signingKey;

    /**
     * 是否权限控制(0:没有;1:有)
     */
    private Boolean isAuthority;

    /**
     * token失效时间
     */
    private Long expiration;

    /**
     * 签名算法
     */
    private String signatureAlgorithm;

    /**
     * 是否同设备一个在线(0:没有限制;1:同设备只有一个)
     */
    private Boolean oneOnline;
}
