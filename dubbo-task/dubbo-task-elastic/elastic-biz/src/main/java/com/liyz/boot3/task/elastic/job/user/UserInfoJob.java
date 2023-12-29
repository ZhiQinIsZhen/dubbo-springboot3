package com.liyz.boot3.task.elastic.job.user;

import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.service.user.bo.UserInfoBO;
import com.liyz.boot3.service.user.remote.RemoteUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shardingsphere.elasticjob.annotation.ElasticJobConfiguration;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/27 11:09
 */
@Slf4j
@ElasticJobConfiguration(jobName = "user", description = "查询用户信息", cron = "0 0/1 * * * ? *", shardingTotalCount = 1, overwrite = true)
public class UserInfoJob implements SimpleJob {

    @DubboReference(group = "task")
    private RemoteUserInfoService remoteUserInfoService;

    @Override
    public void execute(ShardingContext shardingContext) {
        UserInfoBO userInfoBO = remoteUserInfoService.getByUserId(1L);
        log.info("{}", JsonMapperUtil.toJSONString(userInfoBO));
    }
}
