package com.liyz.boot3.service.user.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.user.bo.UserLogoutLogBO;
import com.liyz.boot3.service.user.model.UserLogoutLogDO;
import com.liyz.boot3.service.user.remote.RemoteUserLogoutLogService;
import com.liyz.boot3.service.user.service.UserLogoutLogService;
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
public class RemoteUserLogoutLogServiceImpl implements RemoteUserLogoutLogService {

    @Resource
    private UserLogoutLogService userLogoutLogService;

    /**
     * 根据userId分页查询登出日志
     *
     * @param userId 用户ID
     * @param pageBO 分页信息
     * @return 登出日志
     */
    @Override
    public RemotePage<UserLogoutLogBO> page(Long userId, PageBO pageBO) {
        Page<UserLogoutLogDO> page = userLogoutLogService.page(
                Page.of(pageBO.getPageNum(), pageBO.getPageSize()),
                Wrappers.lambdaQuery(UserLogoutLogDO.builder().userId(userId).build()).orderByDesc(UserLogoutLogDO::getLogoutTime)
        );
        return RemotePage.of(BeanUtil.copyList(page.getRecords(), UserLogoutLogBO::new), page.getTotal(), pageBO.getPageNum(), pageBO.getPageSize());
    }
}
