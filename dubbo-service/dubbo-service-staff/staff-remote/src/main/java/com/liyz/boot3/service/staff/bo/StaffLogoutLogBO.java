package com.liyz.boot3.service.staff.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:16
 */
@Getter
@Setter
public class StaffLogoutLogBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3070437801653890936L;

    private Long staffId;

    /**
     * 登录方式：1:手机;2:邮箱
     */
    private Integer logoutType;

    private Integer device;

    private Date logoutTime;

    private String ip;
}
