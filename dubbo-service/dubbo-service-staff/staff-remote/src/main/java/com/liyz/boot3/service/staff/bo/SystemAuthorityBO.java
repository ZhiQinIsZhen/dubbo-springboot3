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
 * @date 2023/3/11 13:31
 */
@Getter
@Setter
public class SystemAuthorityBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6116744036977982899L;

    /**
     * 权限ID
     */
    private Integer authorityId;

    /**
     * 权限码
     */
    private String authority;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 父权限ID
     */
    private Integer parentAuthorityId;

    /**
     * 应用ID
     */
    private String clientId;
}
