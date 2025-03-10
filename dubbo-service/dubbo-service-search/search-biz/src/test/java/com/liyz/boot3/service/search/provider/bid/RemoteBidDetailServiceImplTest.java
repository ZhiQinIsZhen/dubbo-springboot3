package com.liyz.boot3.service.search.provider.bid;

import com.liyz.boot3.service.search.remote.bid.RemoteBidDetailService;
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
 * @date 2023/11/14 14:55
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteBidDetailServiceImplTest {

    @Resource
    private RemoteBidDetailService remoteBidDetailService;

    @Test
    public void test() {
    }
}