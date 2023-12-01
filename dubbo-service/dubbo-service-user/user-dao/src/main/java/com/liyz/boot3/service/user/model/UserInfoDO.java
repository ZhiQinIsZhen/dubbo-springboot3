package com.liyz.boot3.service.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liyz.boot3.common.dao.model.BaseDO;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:01
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfoDO extends BaseDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3056945666918696574L;

    @TableId(type = IdType.AUTO)
    private Long userId;

    private String realName;

    private String nickName;

    private String mobile;

    private String email;

    private String salt;

    private Date registryTime;
}
