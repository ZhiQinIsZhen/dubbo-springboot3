package com.liyz.boot3.api.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.boot3.api.test.dao.UserLoginLogMapper;
import com.liyz.boot3.api.test.model.UserLoginLogDO;
import com.liyz.boot3.api.test.service.UserLoginLogService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/19 9:44
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLogDO> implements UserLoginLogService {
}
