package com.liyz.boot3.service.search.provider.company;

import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.service.search.bo.company.CompanyBO;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyService;
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
 * @date 2023/11/14 11:16
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteCompanyServiceImplTest {

    @Resource
    private RemoteCompanyService remoteCompanyService;

    @Test
    public void testId() {
        CompanyBO companyBO = remoteCompanyService.getById("d6189b072c60ca0d668e05c006c45db6");
        log.info("value : {}", JsonMapperUtil.toJSONString(companyBO));
    }
}