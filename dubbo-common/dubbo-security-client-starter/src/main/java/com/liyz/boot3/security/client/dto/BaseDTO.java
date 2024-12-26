package com.liyz.boot3.security.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.auth.enums.LoginType;
import lombok.Getter;
import lombok.Setter;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 14:02
 */
@Getter
@Setter
public class BaseDTO {

    /**
     * 认证用户id
     */
    @JsonIgnore
    private Long currentAuthId;

    /**
     * 登录类型
     * @see com.liyz.boot3.service.auth.enums.LoginType
     */
    @JsonIgnore
    private LoginType currentLoginType;

    /**
     * 登录设备
     * @see com.liyz.boot3.service.auth.enums.Device
     */
    @JsonIgnore
    private Device currentDevice;
}
