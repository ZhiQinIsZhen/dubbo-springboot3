package com.liyz.boot3.service.staff.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.staff.bo.StaffLoginLogBO;
import com.liyz.boot3.service.staff.model.StaffLoginLogDO;
import com.liyz.boot3.service.staff.remote.RemoteStaffLoginLogService;
import com.liyz.boot3.service.staff.service.StaffLoginLogService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:50
 */
@DubboService
public class RemoteStaffLoginLogServiceImpl implements RemoteStaffLoginLogService {

    @Resource
    private StaffLoginLogService staffLoginLogService;

    /**
     * 根据staffId分页查询登录日志
     *
     * @param staffId 员工ID
     * @param pageBO 分页信息
     * @return 员工登录日志
     */
    @Override
    public RemotePage<StaffLoginLogBO> page(Long staffId, PageBO pageBO) {
        Page<StaffLoginLogDO> page = staffLoginLogService.page(
                Page.of(pageBO.getPageNum(), pageBO.getPageSize()),
                Wrappers.lambdaQuery(StaffLoginLogDO.builder().staffId(staffId).build())
        );
        return RemotePage.of(BeanUtil.copyList(page.getRecords(), StaffLoginLogBO::new), page.getTotal(), pageBO.getPageNum(), pageBO.getPageSize());
    }
}
