package com.liyz.boot3.service.staff.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liyz.boot3.service.staff.model.base.StaffAuthBaseDO;
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
@TableName("staff_auth_email")
public class StaffAuthEmailDO extends StaffAuthBaseDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4693155700165619110L;

    private String email;
}
