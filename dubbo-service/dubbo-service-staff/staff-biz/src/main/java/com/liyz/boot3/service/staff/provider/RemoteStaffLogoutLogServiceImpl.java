package com.liyz.boot3.service.staff.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.staff.bo.StaffLogoutLogBO;
import com.liyz.boot3.service.staff.model.StaffLogoutLogDO;
import com.liyz.boot3.service.staff.remote.RemoteStaffLogoutLogService;
import com.liyz.boot3.service.staff.service.StaffLogoutLogService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 11:05
 */
@DubboService
public class RemoteStaffLogoutLogServiceImpl implements RemoteStaffLogoutLogService {

    @Resource
    private StaffLogoutLogService staffLogoutLogService;

    /**
     * 根据staffId分页查询登出日志
     *
     * @param staffId 员工ID
     * @param pageBO 分页信息
     * @return 登出日志
     */
    @Override
    public RemotePage<StaffLogoutLogBO> page(Long staffId, PageBO pageBO) {
        Page<StaffLogoutLogDO> page = staffLogoutLogService.page(
                Page.of(pageBO.getPageNum(), pageBO.getPageSize()),
                Wrappers.lambdaQuery(StaffLogoutLogDO.builder().staffId(staffId).build())
        );
        return RemotePage.of(BeanUtil.copyList(page.getRecords(), StaffLogoutLogBO::new), page.getTotal(), pageBO.getPageNum(), pageBO.getPageSize());
    }
}
