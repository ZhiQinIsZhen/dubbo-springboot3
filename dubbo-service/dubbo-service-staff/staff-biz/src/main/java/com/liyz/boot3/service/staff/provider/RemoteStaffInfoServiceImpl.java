package com.liyz.boot3.service.staff.provider;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.exception.util.LoginUserContext;
import com.liyz.boot3.service.staff.bo.StaffInfoBO;
import com.liyz.boot3.service.staff.model.StaffInfoDO;
import com.liyz.boot3.service.staff.remote.RemoteStaffInfoService;
import com.liyz.boot3.service.staff.service.StaffInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:48
 */
@Slf4j
@DubboService
public class RemoteStaffInfoServiceImpl implements RemoteStaffInfoService {

    @Resource
    private StaffInfoService staffInfoService;

    /**
     * 根据staffId获取用户信息
     *
     * @param staffId 员工ID
     * @return 员工信息
     */
    @Override
    public StaffInfoBO getByStaffId(Long staffId) {
        log.info("attachment id : {}", LoginUserContext.getLoginId());
        return BeanUtil.copyProperties(staffInfoService.getById(staffId), StaffInfoBO::new);
    }

    /**
     * 分页查询员工信息
     *
     * @param pageBO 分页信息
     * @return 员工信息
     */
    @Override
    public RemotePage<StaffInfoBO> page(PageBO pageBO) {
        Page<StaffInfoDO> page = staffInfoService.page(Page.of(pageBO.getPageNum(), pageBO.getPageSize()));
        return RemotePage.of(BeanUtil.copyList(page.getRecords(), StaffInfoBO::new), page.getTotal(), pageBO.getPageNum(), pageBO.getPageSize());
    }
}
