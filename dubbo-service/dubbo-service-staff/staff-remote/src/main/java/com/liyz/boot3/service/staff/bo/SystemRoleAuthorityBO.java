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
 * @date 2023/3/11 15:33
 */
@Getter
@Setter
public class SystemRoleAuthorityBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8328113920009535585L;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer authorityId;
}
