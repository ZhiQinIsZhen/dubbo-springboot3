package com.liyz.boot3.common.dao.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.liyz.boot3.exception.util.LoginUserContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

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
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, DEFAULT_CREATE_TIME, LocalDateTime.class, now);
        this.strictInsertFill(metaObject, DEFAULT_UPDATE_TIME, LocalDateTime.class, now);
        this.strictInsertFill(metaObject, DEFAULT_CREATE_USER, Long.class, LoginUserContext.getLoginId());
        this.strictInsertFill(metaObject, DEFAULT_UPDATE_USER, Long.class, LoginUserContext.getLoginId());
        this.strictInsertFill(metaObject, DEFAULT_DELETED, Integer.class, DEFAULT_DELETED_VALUE);
        this.strictInsertFill(metaObject, DEFAULT_VERSION, Integer.class, DEFAULT_VERSION_VALUE);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, DEFAULT_UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, DEFAULT_UPDATE_USER, Long.class, LoginUserContext.getLoginId());
    }
}
