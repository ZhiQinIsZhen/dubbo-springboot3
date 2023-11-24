package com.liyz.boot3.service.staff.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 13:23
 */
@Getter
@Setter
public class SystemRoleBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2197798732348814224L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 父角色ID
     */
    private Integer parentRoleId;
}
