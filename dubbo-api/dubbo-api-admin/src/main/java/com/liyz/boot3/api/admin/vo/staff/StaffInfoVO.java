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
 * @date 2023/3/10 14:42
 */
@Getter
@Setter
public class StaffInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3401036150852744531L;

    @Schema(description = "员工ID")
    private Long staffId;

    @Schema(description = "真实名称")
//    @Desensitization(DesensitizationType.REAL_NAME)
    private String realName;

    @Schema(description = "昵称")
    private String nickName;

//    @Desensitization(DesensitizationType.MOBILE)
    @Schema(description = "手机号码")
    private String mobile;

//    @Desensitization(DesensitizationType.EMAIL)
    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registryTime;
}
