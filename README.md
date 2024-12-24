# Dubbo3 Springboot3 Project

[![Build Status](https://img.shields.io/badge/Build-ZhiQinlsZhen-red)](https://github.com/ZhiQinIsZhen)
[![License](https://img.shields.io/badge/License-MIT-yellow)](https://github.com/ZhiQinIsZhen/auth-netty/blob/main/LICENSE)
![Springboot Version](https://img.shields.io/badge/Springboot-3.4.1-brightgreen)
![Gateway Version](https://img.shields.io/badge/Gateway-4.1.5-brightgreen)
![Admin Version](https://img.shields.io/badge/Admin-3.4.1-brightgreen)
![jjwt Version](https://img.shields.io/badge/jjwt-0.12.6-brightgreen)
![Dubbo Version](https://img.shields.io/badge/Dubbo-3.3.2-brightgreen)
![Mybatis-plus Version](https://img.shields.io/badge/MybatisPlus-3.5.9-brightgreen)
![Sharding-jdbc Version](https://img.shields.io/badge/ShardingJdbc-5.5.1-brightgreen)
![Swagger Version](https://img.shields.io/badge/knife4j-4.4.0-brightgreen)
![ElasticJob Version](https://img.shields.io/badge/elasticjob-3.0.4-brightgreen)
![XxlJob Version](https://img.shields.io/badge/xxljob-2.4.2-brightgreen)
![Flowable Version](https://img.shields.io/badge/flowable-7.1.0-brightgreen)

这是一个基于Jdk21，框架是Dubbo3 + Springboot3的脚手架。

详细介绍下项目中使用的框架：基础架构是[SpringBoot3](https://spring.io/projects/spring-boot)，服务治理是[Apache Dubbo](https://cn.dubbo.apache.org/zh-cn/index.html)，ORM框架选用了[Mybatis-plus](https://baomidou.com/)，注册中心以及配置中心使用了[Nacos](https://nacos.io/zh-cn/)，网关选用了[Gateway](https://spring.io/projects/spring-cloud-gateway)，认证以及资源框架使用了[Spring Security](https://spring.io/projects/spring-security)，接口文档选用了[knife4j](https://doc.xiaominfo.com)，分布式定时任务选用了[ElasticJob](https://shardingsphere.apache.org/elasticjob)，链路追踪使用的是[Skywalking](https://skywalking.apache.org/)，流程框架使用的是[Flowable](https://www.flowable.com/open-source)。

## Skywalking配置

[Skywalking配置文档](https://github.com/ZhiQinIsZhen/dubbo-springboot3/tree/main/document/README.md)

## 项目部署
项目文档请参考文档:[DEPLOY.md](https://github.com/ZhiQinIsZhen/dubbo-springboot3/tree/main/document/DEPLOY.md)

## 项目结构

1. `dubbo-dependencies-bom`：Maven Pom版本管理文件
2. `dubbo-gateway`：前置网关层，集成限流与JWT验证
3. `dubbo-api`：后置网关层，即真正的入口
4. `dubbo-common`：基础包的框架
5. `dubbo-service`：Dubbo的服务提供者，即业务服务
6. `dubbo-task`：分布式定时任务
7. `dubbo-process`：流程引擎

## api结构说明

1. `dubbo-api-admin`: 管理后台网关层，鉴权基于spring-security，认证服务使用的是`dubbo-service-staff`
2. `dubbo-api-user`: 客户前台网关层，鉴权基于spring-security，认证服务使用的是`dubbo-service-user`
3. `dubbo-api-monitor`: 监控平台，认证服务使用的是`dubbo-service-staff`，如需修改可以在`dubbo-service-auth`服务的表中修改`dubbo_tag`
    ```text
    3.1 dubbo-service中没有注册上monitor，是因为没有加 spring-boot-starter-web 依赖，如果需要加入monitor中，则需要增加该依赖，
        因为health的数据获取是通过http来请求的
    ```

## common结构说明

1. `dubbo-common-api-starter`: 通用web或者网关层框架
    ```text
    1.1 全局异常拦截器
    1.2 Swagger基础配置
    1.3 Spring MVC的全局配置：Jackson、Error重定向、国际化
    ```
2. `dubbo-common-dao-starter`: 通用DAO层的框架(基于Mybatis-plus)
    ```text
    2.1 数据库字段加解密、脱敏
    2.2 Mybatis-plus的插件：乐观锁、防止全表更新与删除、分页
    2.3 公共字段的赋值
    2.4 LocalTransactionTemplate: 本地事务调用模板
    ```
3. `dubbo-common-search-starter`: 通用Elastic Search层的框架(借鉴与Mybatis-plus，继承EsMapper即可开箱即用)
    ````text
    3.1 参考easy-es：后续该模块可能会被删除
    ````
4. `dubbo-common-lock-starter`: 通用Lock框架
   ```text
    4.1 RedisLockUtil: 基于Redisson封装的分布式锁工具
    4.2 ZookeeperLockUtil: 后续会增加基于zk封装的分布式锁工具
    ```
5. `dubbo-common-remote`: 通用Dubbo远程接口框架(包含了参数验证器:validation)
    ```text
    5.1 Dubbo远程服务提供jar的依赖：主要就是基础业务异常与通用枚举
    ```
6. `dubbo-common-service`: 业务通用核心框架
    ```text
    6.1 通用常量池
    6.2 BeanUtil工具类
    ```
7. `dubbo-common-util`: 通用工具类框架
    ```text
    7.1 脱敏注解@Desensitization：支持10中类型（加解密、DFA、手机号等）
    7.2 去除首尾空格注解@Trim
    7.3 公告工具类：时间、Json、加解密等
    ```
8. `dubbo-exception-filter`: Dubbo自定义异常过滤器
    ```text
    8.1 Dubbo对自定义业务异常过滤
    ```
9. `dubbo-security-client-starter`: security-client，适用于各个网关服务中
    ```text
    9.1 匿名访问注解@Anonymous：对于不需要登录校验的接口直接在类或者方法上加上该注解，而不需要配置到Security的配置中
    9.2 认证上下文：AuthContext
    9.3 登录设备上下文：DeviceContext
    9.4 自定义参数解析器：AuthUserArgumentResolver
    ```

## service结构说明

1. `dubbo-service-auth`: 认证资源服务，基于spring-security以及jwt
2. `dubbo-service-staff`: 员工信息服务
3. `dubbo-service-user`: 客户信息服务
4. `dubbo-service-search`：搜索服务

## gateway结构说明

1. `GlobalJWTFilter`: 自定义全局JWT过滤器
2. `GlobalLimitFilter`: 自定义限流过滤器

## task结构说明
1. `dubbo-task-elastic`：使用的是基于elastic job为框架
2. `dubbo-task-xxl`：使用的是基于xxl-job为框架

## process结构说明
1. `dubbo-process-flowable`：使用的是基于flowable为框架，由于最新的7.0.0版本中不在包含flowable-ui，所以ui版本需要下载最新的6.x.x版本

## 如需之前版本，请关注tag标签，重新拉取tag代码

## 开源共建
1. 如有问题可以提交[issue](https://github.com/ZhiQinIsZhen/dubbo-springboot3/issues)
2. 如有需要Spring Cloud，请点击[Spring Cloud](https://github.com/ZhiQinIsZhen/springcloud-demo)
3. 如有需要Springboot2版本，请点击[dubbo-springboot-nacos](https://github.com/ZhiQinIsZhen/dubbo-springboot-nacos)

## License
Dubbo Springboot3 Project is under the MIT License.