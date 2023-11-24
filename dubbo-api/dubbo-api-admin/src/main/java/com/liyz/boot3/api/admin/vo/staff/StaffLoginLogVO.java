package com.liyz.boot3.api.admin.vo.staff;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * @date 2023/3/10 16:56
 */
@Getter
@Setter
public class StaffLoginLogVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 378737454967076747L;

    @Schema(description = "员工ID")
    private Long staffId;

    @Schema(description = "登出方式(1:手机;2:邮箱)")
    private Integer loginType;

    @Schema(description = "登出设备(1移动端:;2:网页端)")
    private Integer device;

    @Schema(description = "登出时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    @Schema(description = "IP地址")
    private String ip;
}
