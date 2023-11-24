package com.liyz.boot3.service.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyz.boot3.service.auth.model.AuthSourceDO;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 18:00
 */
public interface AuthSourceService extends IService<AuthSourceDO> {

    /**
     * 根据资源ID获取资源配置信息
     *
     * @param clientId 资源ID
     * @return 资源配置信息
     */
    AuthSourceDO getByClientId(String clientId);
}
