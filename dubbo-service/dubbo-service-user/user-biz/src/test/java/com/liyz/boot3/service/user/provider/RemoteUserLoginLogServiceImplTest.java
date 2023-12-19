package com.liyz.boot3.service.user.provider;

import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.service.user.bo.UserLoginLogBO;
import com.liyz.boot3.service.user.remote.RemoteUserLoginLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/19 11:32
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteUserLoginLogServiceImplTest {

    @Resource
    private RemoteUserLoginLogService remoteUserLoginLogService;

    @Test
    public void test() {
        PageBO pageBO = new PageBO();
        pageBO.setPageNum(1L);
        pageBO.setPageSize(100L);
        RemotePage<UserLoginLogBO> remotePage = remoteUserLoginLogService.pageStream(1L, pageBO);
        log.info("value:{}", JsonMapperUtil.toJSONString(remotePage));
    }
}