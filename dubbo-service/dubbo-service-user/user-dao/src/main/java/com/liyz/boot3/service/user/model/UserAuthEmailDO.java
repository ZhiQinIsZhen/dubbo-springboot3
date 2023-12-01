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
 * @date 2023/3/10 9:58
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_auth_email")
public class UserAuthEmailDO extends UserAuthBaseDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4693155700165619110L;

    private String email;
}
