# Dubbo3 Springboot3 Project

[![Build Status](https://img.shields.io/badge/Build-ZhiQinlsZhen-red)](https://github.com/ZhiQinIsZhen)
[![License](https://img.shields.io/badge/License-MIT-yellow)](https://github.com/ZhiQinIsZhen/auth-netty/blob/main/LICENSE)
![Springboot Version](https://img.shields.io/badge/Springboot-3.4.5-brightgreen)
![Gateway Version](https://img.shields.io/badge/Gateway-4.1.5-brightgreen)
![Admin Version](https://img.shields.io/badge/Admin-3.4.5-brightgreen)
![jjwt Version](https://img.shields.io/badge/jjwt-0.12.6-brightgreen)
![Dubbo Version](https://img.shields.io/badge/Dubbo-3.3.4-brightgreen)
![Mybatis-plus Version](https://img.shields.io/badge/MybatisPlus-3.5.12-brightgreen)
![Sharding-jdbc Version](https://img.shields.io/badge/ShardingJdbc-5.5.2-brightgreen)
![Swagger Version](https://img.shields.io/badge/knife4j-4.4.0-brightgreen)
![ElasticJob Version](https://img.shields.io/badge/elasticjob-3.0.4-brightgreen)
![XxlJob Version](https://img.shields.io/badge/xxljob-3.1.0-brightgreen)
![Flowable Version](https://img.shields.io/badge/flowable-7.1.0-brightgreen)

该项目基于JDK21 + Dubbo(3.3.x) + Springboot(3.4.x) 来进行搭建，主要提供Gateway层的认证、鉴权、限流，以及数据层面的分库分表的解决方案。
![登录过程](/document/login.png)
![认证过程](/document/authority.png)

## 项目搭建框架

- JDK：选择JDK21版本，因为该版本是长期支持的一个版本，并且支持虚拟线程，使得多数据组装在不使用多线程的情况下有更高效的查询。
- [SpringBoot](https://spring.io/projects/spring-boot)：选择最新的3.4.x版本，当然了也可以选择3.3.x版本，在该项目中会持续迭代，使用最新版本来支撑。
- [Apache Dubbo](https://cn.dubbo.apache.org/zh-cn/index.html)：选择最新的3.3.x版本，在3.3.x版本中，Dubbo来支持云原生框架来适应云部署。
- [Gateway](https://spring.io/projects/spring-cloud-gateway)：网关选用Springcloud-gateway，并在其进行统一的认证、鉴权、限流，使得业务开发更加专注。
- [Spring Security](https://spring.io/projects/spring-security)：认证框架选用Spring-security，全家桶，兼容性更强。
- [Mybatis-plus](https://baomidou.com/)：ORM选择Mybatis-plus，它会使得Java程序猿更加专注业务逻辑，而不用生成或者编写sql.xml文件，从而提高开发效率。
- [Nacos](https://nacos.io/zh-cn/)：作为所有服务的注册、配置中心，阿里出品，必属精品。
- [knife4j](https://doc.xiaominfo.com)：接口文档使用它，因为页面清晰，对接、调试两不误，开发也不关心繁琐的文档编写，边写代码边生成文档。
- [Skywalking](https://skywalking.apache.org/)：方便问题追踪，有兴趣的同学可以前往我的另一个项目[skywalking-java](https://github.com/ZhiQinIsZhen/skywalking-java)，输出日志自动注入链路ID，并将链路ID返回到Response Header中。
   ```properties
   # jvm启动参数
   -javaagent:C:\skywalking-java\skywalking-agent\skywalking-agent.jar
   -Dskywalking.agent.keep_tracing=true
   -Dskywalking.agent.service_name=dubbo-service-auth
   -Dskywalking.collector.backend_service=localhost:11800
   
   # Remote Debug配置监听
   -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=6666
   ```
  
- Job：支持[ElasticJob](https://shardingsphere.apache.org/elasticjob)，同时也支持[Xxl-Job](https://www.xuxueli.com/xxl-job/)
- [Flowable](https://www.flowable.com/open-source)：后续会搭建出流程框架的使用。

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
## gateway说明
1. `GlobalRequestTimeFilter`: 请求耗时打印
2. `GlobalCacheBodyFilter`: 缓存body参数
3. `GlobalJWTFilter`: 登录认证，可配置匿名访问url
4. `GlobalLimitFilter`: 限流过滤器，如果登录则按照用户id，否则根据ip限流
5. `GlobalAuthorityFilter`: 权限过滤器，可配置白名单url
6. `GlobalAuthInfoHeaderFilter`: 认证信息透传过滤器
7. Filter执行顺序：RequestTime -> CacheBody -> JWT -> Limit -> Authority -> AuthInfoHeader

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
    7.1 脱敏注解@Desensitization：支持10中类型（加解密、DFA、手机号等），支持Jackson序列化以及mysql
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