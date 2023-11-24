package com.liyz.boot3.service.staff.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liyz.boot3.common.dao.model.BaseDO;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 11:46
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("system_authority")
public class SystemAuthorityDO extends BaseDO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7111985038475734345L;

    @TableId(type = IdType.INPUT)
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
