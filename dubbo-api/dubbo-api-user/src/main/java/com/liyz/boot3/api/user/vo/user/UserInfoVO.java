package com.liyz.boot3.api.user.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liyz.boot3.common.util.annotation.Desensitization;
import com.liyz.boot3.common.util.constant.DesensitizationType;
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
public class UserInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3401036150852744531L;

    @Schema(description = "客户ID")
    private Long userId;

    @Schema(description = "真实名称")
    @Desensitization(DesensitizationType.REAL_NAME)
    private String realName;

    @Schema(description = "昵称")
    private String nickName;

    @Desensitization(DesensitizationType.MOBILE)
    @Schema(description = "手机号码")
    private String mobile;

    @Desensitization(DesensitizationType.EMAIL)
    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registryTime;
}
