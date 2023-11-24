package com.liyz.boot3.service.staff.remote;

import com.liyz.boot3.service.staff.bo.SystemAuthorityBO;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 13:32
 */
public interface RemoteSystemAuthorityService {

    /**
     * 新增一个权限项
     *
     * @param systemAuthorityBO 系统授权项参数
     * @return 系统授权项
     */
    SystemAuthorityBO addSystemAuthority(SystemAuthorityBO systemAuthorityBO);

    /**
     * 查询权限项列表
     *
     * @return 系统授权项
     */
    List<SystemAuthorityBO> list();
}
