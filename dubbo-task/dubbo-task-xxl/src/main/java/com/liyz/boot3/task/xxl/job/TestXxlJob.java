package com.liyz.boot3.task.xxl.job;

import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/15 10:30
 */
@Slf4j
@Service
public class TestXxlJob extends IJobHandler {

    @Override
    public void execute() throws Exception {
        log.info("testXxlJob start");
    }
}
