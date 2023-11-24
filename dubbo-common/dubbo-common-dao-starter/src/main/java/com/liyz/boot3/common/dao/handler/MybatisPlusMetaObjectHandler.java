package com.liyz.boot3.common.dao.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.liyz.boot3.exception.util.LoginUserContext;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/23 16:56
 */
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    String DEFAULT_CREATE_TIME = "createTime";

    String DEFAULT_UPDATE_TIME = "updateTime";

    String DEFAULT_CREATE_USER = "createUser";

    String DEFAULT_UPDATE_USER = "updateUser";

    String DEFAULT_DELETED = "isDeleted";

    String DEFAULT_VERSION = "version";

    Integer DEFAULT_DELETED_VALUE = 0;

    Integer DEFAULT_VERSION_VALUE = 0;

    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        setFieldValByName(DEFAULT_CREATE_TIME, now, metaObject);
        setFieldValByName(DEFAULT_UPDATE_TIME, now, metaObject);
        setFieldValByName(DEFAULT_CREATE_USER, LoginUserContext.getLoginId(), metaObject);
        setFieldValByName(DEFAULT_UPDATE_USER, LoginUserContext.getLoginId(), metaObject);
        setFieldValByName(DEFAULT_DELETED, DEFAULT_DELETED_VALUE, metaObject);
        setFieldValByName(DEFAULT_VERSION, DEFAULT_VERSION_VALUE, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(DEFAULT_UPDATE_TIME, new Date(), metaObject);
        setFieldValByName(DEFAULT_UPDATE_USER, LoginUserContext.getLoginId(), metaObject);
    }
}
