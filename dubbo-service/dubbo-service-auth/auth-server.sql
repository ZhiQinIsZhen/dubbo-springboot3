CREATE TABLE `auth_source`  (
     `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
     `client_id` varchar(128) NOT NULL COMMENT '客户端id',
     `client_tag` varchar(32) NOT NULL COMMENT 'dubbo tag',
     `create_user` bigint(20) NOT NULL COMMENT '创建者',
     `update_user` bigint(20) NOT NULL COMMENT '更新者',
     `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
     `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 | 0、未删除 1、已删除',
     `version` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本号',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `uniq_client_id`(`client_id`) USING BTREE,
     KEY `idx_auth_source_client_id` (`client_id`,`client_tag`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '认证服务应用表';

-- ----------------------------
-- Records of auth_application
-- ----------------------------
INSERT INTO `auth_application` VALUES
(1, 'dubbo-api-admin', 'staff', -1, -1, now(), now(), 0, 0),
(2, 'dubbo-api-user', 'user', -1, -1, now(), now(), 0, 0);

CREATE TABLE `auth_jwt1` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `client_id` varchar(128) NOT NULL COMMENT '客户端id',
    `jwt_prefix` varchar(32) NOT NULL COMMENT 'token前缀',
    `signing_key` varchar(16) NOT NULL COMMENT 'jwt的签名key',
    `expiration` bigint(20) NOT NULL DEFAULT '604800' COMMENT 'jwt的签名失效时间(s)',
    `signature_algorithm` varchar(16) NOT NULL DEFAULT 'HS512' COMMENT 'jwt的签名算法',
    `is_authority` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否权限控制(0:没有;1:有)',
    `one_online` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同设备一个在线(0:没有限制;1:同设备只有一个)',
    `create_user` bigint(20) NOT NULL COMMENT '创建者',
    `update_user` bigint(20) NOT NULL COMMENT '更新者',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
    `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_client_id` (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证服务jwt信息配置表';

INSERT INTO `auth`.`auth_jwt` VALUES
(1, 'dubbo-api-admin', 'Bearer ', 'Bonnie', 604800, 'HS512', 0, 1, -1, -1, now(), now(), 0, 0),
(2, 'dubbo-api-user', 'Bearer ', 'Bonnie', 604800, 'HS512', 0, 1, -1, -1, now(), now(), 0, 0),
(3, 'dubbo-service-auth', 'Bearer ', 'Bonnie', 604800, 'HS512', 0, 1, -1, -1, now(), now(), 0, 0);
