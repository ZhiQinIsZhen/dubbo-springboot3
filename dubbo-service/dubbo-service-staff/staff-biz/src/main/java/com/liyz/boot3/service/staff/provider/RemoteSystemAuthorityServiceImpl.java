package com.liyz.boot3.service.staff.provider;

import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.staff.bo.SystemAuthorityBO;
import com.liyz.boot3.service.staff.model.SystemAuthorityDO;
import com.liyz.boot3.service.staff.remote.RemoteSystemAuthorityService;
import com.liyz.boot3.service.staff.service.SystemAuthorityService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 15:30
 */
@DubboService
public class RemoteSystemAuthorityServiceImpl implements RemoteSystemAuthorityService {

    @Resource
    private SystemAuthorityService systemAuthorityService;

    /**
     * 新增一个权限项
     *
     * @param systemAuthorityBO 系统授权项参数
     * @return 系统授权项
     */
    @Override
    public SystemAuthorityBO addSystemAuthority(SystemAuthorityBO systemAuthorityBO) {
        systemAuthorityService.save(BeanUtil.copyProperties(systemAuthorityBO, SystemAuthorityDO::new));
        return systemAuthorityBO;
    }

    /**
     * 查询权限项列表
     *
     * @return 系统授权项
     */
    @Override
    public List<SystemAuthorityBO> list() {
        return BeanUtil.copyList(systemAuthorityService.list(), SystemAuthorityBO::new);
    }
}
