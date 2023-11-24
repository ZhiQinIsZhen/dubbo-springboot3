CREATE TABLE `user_auth_email` (
   `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
   `email` varchar(128) NOT NULL COMMENT '邮箱',
   `password` varchar(256) NOT NULL COMMENT '密码',
   `create_user` bigint(20) NOT NULL COMMENT '创建者',
   `update_user` bigint(20) NOT NULL COMMENT '更新者',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
   `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
   PRIMARY KEY (`user_id`) USING BTREE,
   UNIQUE KEY `uniq_email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户邮箱认证表';

CREATE TABLE `user_auth_mobile` (
    `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `mobile` varchar(11) NOT NULL COMMENT '邮箱',
    `password` varchar(256) NOT NULL COMMENT '密码',
    `create_user` bigint(20) NOT NULL COMMENT '创建者',
    `update_user` bigint(20) NOT NULL COMMENT '更新者',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
    `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE KEY `uniq_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户手机认证表';

CREATE TABLE `user_info` (
     `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
     `real_name` varchar(128) DEFAULT NULL COMMENT '真实名称',
     `nick_name` varchar(128) NOT NULL COMMENT '昵称',
     `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
     `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
     `salt` varchar(128) DEFAULT NULL COMMENT '加密盐',
     `registry_time` timestamp NOT NULL COMMENT '注册时间',
     `create_user` bigint(20) NOT NULL COMMENT '创建者',
     `update_user` bigint(20) NOT NULL COMMENT '更新者',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
     `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
     PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基础信息表';

CREATE TABLE `user_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `login_type` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '登录方式：1:手机;2:邮箱',
  `device` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '登录设备：1:手机;2:web',
  `login_time` timestamp NOT NULL COMMENT '登录时间',
  `ip` varchar(256) DEFAULT NULL COMMENT '登录IP地址',
  `create_user` bigint(20) NOT NULL COMMENT '创建者',
  `update_user` bigint(20) NOT NULL COMMENT '更新者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
  `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录日志表';

CREATE TABLE `user_logout_log` (
   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
   `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
   `logout_type` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '登出方式：1:手机;2:邮箱',
   `device` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '登录设备：1:手机;2:web',
   `logout_time` timestamp NOT NULL COMMENT '登录时间',
   `ip` varchar(256) DEFAULT NULL COMMENT '登录IP地址',
   `create_user` bigint(20) NOT NULL COMMENT '创建者',
   `update_user` bigint(20) NOT NULL COMMENT '更新者',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
   `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
   PRIMARY KEY (`id`) USING BTREE,
   KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登出日志表';