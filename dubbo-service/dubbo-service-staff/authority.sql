CREATE TABLE `staff_role` (
     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
     `staff_id` bigint(20) unsigned NOT NULL COMMENT '员工ID',
     `role_id` int(8) unsigned NOT NULL COMMENT '角色ID',
     `create_user` bigint(20) NOT NULL COMMENT '创建者',
     `update_user` bigint(20) NOT NULL COMMENT '更新者',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
     `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE KEY `uniq_staff_id_role_id` (`staff_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工角色表';

CREATE TABLE `system_role` (
   `role_id` int(8) unsigned NOT NULL COMMENT '角色ID',
   `role_name` varchar(32) NOT NULL COMMENT '角色名称',
   `parent_role_id` int(8) unsigned DEFAULT NULL COMMENT '父角色ID',
   `create_user` bigint(20) NOT NULL COMMENT '创建者',
   `update_user` bigint(20) NOT NULL COMMENT '更新者',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
   `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
   PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

CREATE TABLE `system_authority` (
    `authority_id` int(8) unsigned NOT NULL COMMENT '权限ID',
    `authority` varchar(32) NOT NULL COMMENT '权限CODE',
    `authority_name` varchar(32) NOT NULL COMMENT '权限名称',
    `parent_authority_id` int(8) unsigned DEFAULT NULL COMMENT '父权限ID',
    `client_id` varchar(128) NOT NULL COMMENT '应用ID',
    `create_user` bigint(20) NOT NULL COMMENT '创建者',
    `update_user` bigint(20) NOT NULL COMMENT '更新者',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
    `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`authority_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

CREATE TABLE `system_role_authority` (
     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
     `role_id` int(8) unsigned NOT NULL COMMENT '角色ID',
     `authority_id` int(8) unsigned NOT NULL COMMENT '权限ID',
     `create_user` bigint(20) NOT NULL COMMENT '创建者',
     `update_user` bigint(20) NOT NULL COMMENT '更新者',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
     `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE KEY `uniq_role_id_authority_id` (`role_id`,`authority_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色权限表';

CREATE TABLE `staff_authority` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `staff_id` bigint(20) unsigned NOT NULL COMMENT '员工ID',
  `authority_id` int(8) unsigned NOT NULL COMMENT '权限ID',
  `authority_end_time` timestamp NOT NULL COMMENT '权限截止日期',
  `create_user` bigint(20) NOT NULL COMMENT '创建者',
  `update_user` bigint(20) NOT NULL COMMENT '更新者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
  `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_staff_id_authority_id` (`staff_id`,`authority_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工权限表';