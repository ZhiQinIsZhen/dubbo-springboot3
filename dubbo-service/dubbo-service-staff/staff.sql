CREATE TABLE `staff_auth_email` (
   `staff_id` bigint(20) unsigned NOT NULL COMMENT '员工ID',
   `email` varchar(128) NOT NULL COMMENT '邮箱',
   `password` varchar(256) NOT NULL COMMENT '密码',
   `create_user` bigint(20) NOT NULL COMMENT '创建者',
   `update_user` bigint(20) NOT NULL COMMENT '更新者',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
   `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
   PRIMARY KEY (`staff_id`) USING BTREE,
   UNIQUE KEY `uniq_email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工邮箱认证表';

CREATE TABLE `staff_auth_mobile` (
    `staff_id` bigint(20) unsigned NOT NULL COMMENT '员工ID',
    `mobile` varchar(11) NOT NULL COMMENT '邮箱',
    `password` varchar(256) NOT NULL COMMENT '密码',
    `create_user` bigint(20) NOT NULL COMMENT '创建者',
    `update_user` bigint(20) NOT NULL COMMENT '更新者',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
    `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`staff_id`) USING BTREE,
    UNIQUE KEY `uniq_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工手机认证表';

CREATE TABLE `staff_info` (
     `staff_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '员工ID',
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
     PRIMARY KEY (`staff_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工基础信息表';

CREATE TABLE `staff_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `staff_id` bigint(20) unsigned NOT NULL COMMENT '员工ID',
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
  KEY `idx_staff_id` (`staff_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工登录日志表';

CREATE TABLE `staff_logout_log` (
   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
   `staff_id` bigint(20) unsigned NOT NULL COMMENT '员工ID',
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
   KEY `idx_staff_id` (`staff_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工登出日志表';