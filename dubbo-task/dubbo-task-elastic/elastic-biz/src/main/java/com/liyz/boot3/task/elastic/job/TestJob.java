package com.liyz.boot3.task.elastic.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.annotation.ElasticJobConfiguration;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/27 10:56
 */
@Slf4j
@ElasticJobConfiguration(jobName = "Test", description = "测试", cron = "0/10 * * * * ?", shardingTotalCount = 1)
public class TestJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("test job : {}", System.currentTimeMillis());
    }
}
