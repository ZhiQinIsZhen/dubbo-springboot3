
package com.liyz.boot3.service.user.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.liyz.boot3.common.dao.model.BaseDO;
import lombok.Getter;
import lombok.Setter;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/14 9:26
 */
@Getter
@Setter
public class UserAuthBaseDO extends BaseDO {

    /**
     * 用户ID
     */
    @TableId(type = IdType.INPUT)
    private Long userId;

    /**
     * 密码
     */
    private String password;
}
