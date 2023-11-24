package com.liyz.boot3.service.staff.provider;

import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.staff.bo.SystemRoleBO;
import com.liyz.boot3.service.staff.model.SystemRoleDO;
import com.liyz.boot3.service.staff.remote.RemoteSystemRoleService;
import com.liyz.boot3.service.staff.service.SystemRoleService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 13:26
 */
@DubboService
public class RemoteSystemRoleServiceImpl implements RemoteSystemRoleService {

    @Resource
    private SystemRoleService systemRoleService;

    /**
     * 创建一个角色
     *
     * @param systemRoleBO 系统角色参数
     * @return 系统角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemRoleBO addSystemRole(SystemRoleBO systemRoleBO) {
        systemRoleService.save(BeanUtil.copyProperties(systemRoleBO, SystemRoleDO::new));
        return systemRoleBO;
    }

    /**
     * 查询角色列表
     *
     * @return 系统角色列表
     */
    @Override
    public List<SystemRoleBO> list() {
        return BeanUtil.copyList(systemRoleService.list(), SystemRoleBO::new);
    }
}
