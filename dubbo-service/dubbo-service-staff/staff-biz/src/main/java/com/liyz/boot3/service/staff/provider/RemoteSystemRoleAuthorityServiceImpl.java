package com.liyz.boot3.service.staff.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.staff.bo.SystemRoleAuthorityBO;
import com.liyz.boot3.service.staff.model.SystemRoleAuthorityDO;
import com.liyz.boot3.service.staff.remote.RemoteSystemRoleAuthorityService;
import com.liyz.boot3.service.staff.service.SystemRoleAuthorityService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 15:38
 */
@DubboService
public class RemoteSystemRoleAuthorityServiceImpl implements RemoteSystemRoleAuthorityService {

    @Resource
    private SystemRoleAuthorityService systemRoleAuthorityService;

    /**
     * 给一个权限项绑定一个角色
     *
     * @param systemRoleAuthorityBO 系统角色授权项
     * @return 是否成功
     */
    @Override
    public Boolean bindRole(SystemRoleAuthorityBO systemRoleAuthorityBO) {
        return systemRoleAuthorityService.save(BeanUtil.copyProperties(systemRoleAuthorityBO, SystemRoleAuthorityDO::new));
    }

    /**
     * 根据一个角色查询出权限项
     *
     * @param roleId 角色ID
     * @return 系统角色授权列表
     */
    @Override
    public List<SystemRoleAuthorityBO> getByRoleId(Integer roleId) {
        return BeanUtil.copyList(systemRoleAuthorityService.list(Wrappers.query(SystemRoleAuthorityDO.builder().roleId(roleId).build())), SystemRoleAuthorityBO::new);
    }
}
