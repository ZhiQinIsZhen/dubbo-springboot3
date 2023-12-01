package com.liyz.boot3.service.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liyz.boot3.service.user.model.base.UserAuthBaseDO;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 9:59
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_auth_mobile")
public class UserAuthMobileDO extends UserAuthBaseDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5417331084800908347L;

    private String mobile;
}
