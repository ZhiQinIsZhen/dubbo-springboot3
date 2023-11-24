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
 * @date 2023/3/9 15:38
 */
@Getter
@Setter
@TableName("auth_source")
public class AuthSourceDO extends BaseDO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5151397049526247715L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用名
     */
    private String clientId;

    /**
     * dubbo tag
     */
    private String clientTag;
}
