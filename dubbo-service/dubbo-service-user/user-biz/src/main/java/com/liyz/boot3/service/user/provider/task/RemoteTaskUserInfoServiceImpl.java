package com.liyz.boot3.service.user.provider.task;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.user.bo.UserInfoBO;
import com.liyz.boot3.service.user.model.UserInfoDO;
import com.liyz.boot3.service.user.remote.RemoteUserInfoService;
import com.liyz.boot3.service.user.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/28 13:52
 */
@DubboService(group = "task", executor = "task-executor")
public class RemoteTaskUserInfoServiceImpl implements RemoteUserInfoService {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 根据userId获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserInfoBO getByUserId(Long userId) {
        return BeanUtil.copyProperties(userInfoService.getById(userId), UserInfoBO::new);
    }

    /**
     * 分页查询客户信息
     *
     * @param pageBO 分页参数
     * @return 客户信息
     */
    @Override
    public RemotePage<UserInfoBO> page(PageBO pageBO) {
        Page<UserInfoDO> page = userInfoService.page(Page.of(pageBO.getPageNum(), pageBO.getPageSize()));
        return RemotePage.of(BeanUtil.copyList(page.getRecords(), UserInfoBO::new), page.getTotal(), pageBO.getPageNum(), pageBO.getPageSize());
    }
}
