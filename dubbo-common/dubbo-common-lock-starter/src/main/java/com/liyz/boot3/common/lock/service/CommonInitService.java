package com.liyz.boot3.common.lock.service;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/10/19 9:56
 */
public interface CommonInitService {

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    ApplicationContext getApplicationContext();

    /**
     * 获取文件流
     *
     * @param file 文件路劲
     * @return 文件流
     * @throws IOException 异常
     */
    default InputStream getConfigStream(String file) throws IOException {
        Resource resource = getApplicationContext().getResource(file);
        return resource.getInputStream();
    }


}
