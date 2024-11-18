package com.liyz.boot3.service.user.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.service.user.bo.UserLoginLogBO;
import com.liyz.boot3.service.user.model.UserLoginLogDO;
import com.liyz.boot3.service.user.remote.RemoteUserLoginLogService;
import com.liyz.boot3.service.user.service.UserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:50
 */
@Slf4j
@DubboService
public class RemoteUserLoginLogServiceImpl implements RemoteUserLoginLogService {

    @Resource
    private UserLoginLogService userLoginLogService;

    /**
     * 根据userId分页查询登录日志
     *
     * @param userId 用户ID
     * @param pageBO 分页参数
     * @return 用户登录日志
     */
    @Override
    public RemotePage<UserLoginLogBO> page(Long userId, PageBO pageBO) {
        Page<UserLoginLogDO> page = userLoginLogService.page(
                Page.of(pageBO.getPageNum(), pageBO.getPageSize()),
                Wrappers.lambdaQuery(UserLoginLogDO.class)
                        .select(UserLoginLogDO::getUserId, UserLoginLogDO::getLoginType, UserLoginLogDO::getDevice,
                                UserLoginLogDO::getLoginTime, UserLoginLogDO::getIp)
                        .eq(UserLoginLogDO::getUserId, userId)
                        .orderByDesc(UserLoginLogDO::getLoginTime)
        );
        return RemotePage.of(BeanUtil.copyList(page.getRecords(), UserLoginLogBO::new), page.getTotal(), pageBO.getPageNum(), pageBO.getPageSize());
    }

    /**
     * 根据userId分页流式查询登录日志
     *
     * @param userId 用户ID
     * @param pageBO 分页参数
     * @return 用户登录日志
     */
    @Override
    public RemotePage<UserLoginLogBO> pageStream(Long userId, PageBO pageBO) {
        List<UserLoginLogBO> list = new ArrayList<>();
        Page<UserLoginLogDO> page = Page.of(pageBO.getPageNum(), pageBO.getPageSize());
        userLoginLogService.pageStream(page, Wrappers.lambdaQuery(UserLoginLogDO.builder().userId(userId).build()), resultContext -> {
            log.info("当前处理第{}条记录", resultContext.getResultCount());
            log.info("当前记录:{}", JsonMapperUtil.toJSONString(resultContext.getResultObject()));
            list.add(BeanUtil.copyProperties(resultContext.getResultObject(), UserLoginLogBO::new));
        });
        //todo 这里pageTotal不准确，需要自己逻辑补充
        return RemotePage.of(list, page.getTotal(), pageBO.getPageNum(), pageBO.getPageSize());
    }
}
