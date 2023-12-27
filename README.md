# Dubbo Springboot3 Project

[![Build Status](https://img.shields.io/badge/Build-ZhiQinlsZhen-red)](https://github.com/ZhiQinIsZhen/dubbo-springboot3)
[![License](https://img.shields.io/badge/License-MIT-yellow)](https://github.com/ZhiQinIsZhen/dubbo-springboot3/blob/main/LICENSE)
![Springboot Version](https://img.shields.io/badge/Springboot-3.2.1-brightgreen)
![Gateway Version](https://img.shields.io/badge/Gateway-4.3.0-brightgreen)
![jjwt Version](https://img.shields.io/badge/jjwt-0.12.3-brightgreen)
![Dubbo Version](https://img.shields.io/badge/Dubbo-3.3.0(beta1)-brightgreen)
![Mybatis-plus Version](https://img.shields.io/badge/MybatisPlus-3.5.5-brightgreen)
![Swagger Version](https://img.shields.io/badge/knife4j-4.4.0-brightgreen)
![Elasticjob Version](https://img.shields.io/badge/elasticjob-3.0.4-brightgreen)

这是一个Springboot3项目的脚手架，基于Jdk21。
使用的是[SpringBoot3](https://spring.io/projects/spring-boot) + 
[Apache Dubbo](https://cn.dubbo.apache.org/zh-cn/index.html)，
ORM框架选用了[Mybatis-plus](https://baomidou.com/)，
注册中心以及配置中心使用了[Nacos](https://nacos.io/zh-cn/)，
网关选用了[Gateway](https://spring.io/projects/spring-cloud-gateway)，
认证以及资源框架使用了[Spring Security](https://spring.io/projects/spring-security)，
分布式定时任务选用了[ElasticJob](https://shardingsphere.apache.org/elasticjob)，
链路追踪使用的是[Skywalking](https://skywalking.apache.org/)。

## Skywalking配置

```properties
-javaagent:C:\skywalking-java\skywalking-agent\skywalking-agent.jar
-Dskywalking.agent.keep_tracing=true
-Dskywalking.agent.service_name=dubbo-service-auth
```
## Remote Debug配置监听

```properties
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=6666
```

## 项目结构

1. `dubbo-dependencies-bom`：Maven Pom版本管理文件
2. `dubbo-gateway`：前置网关层，集成限流与JWT验证
3. `dubbo-api`：后置网关层，即真正的入口
4. `dubbo-common`：基础包的框架
5. `dubbo-service`：Dubbo的服务提供者，即业务服务
6. `dubbo-task`：分布式定时任务

## api结构说明

1. `dubbo-api-admin`: 管理后台网关层，鉴权基于spring-security
2. `dubbo-api-user`: 客户前台网关层，鉴权基于spring-security

## common结构说明

1. `dubbo-common-api-starter`: 通用web或者网关层框架
2. `dubbo-common-dao-starter`: 通用DAO层的框架(基于Mybatis-plus)
3. `dubbo-common-es-starter`: 通用Elastic Search层的框架(借鉴与Mybatis-plus，继承EsMapper即可开箱即用)
4. `dubbo-common-lock-starter`: 通用Lock框架
5. `dubbo-common-remote`: 通用Dubbo远程接口框架(包含了参数验证器:validation)
6. `dubbo-common-service`: 业务通用核心框架
7. `dubbo-common-util`: 通用工具类框架
8. `dubbo-exception-filter`: Dubbo自定义异常过滤器
9. `dubbo-security-client-starter`: security-client，适用于各个网关服务中

## service结构说明

1. `dubbo-service-auth`: 认证资源服务，基于spring-security以及jwt
2. `dubbo-service-staff`: 员工信息服务
3. `dubbo-service-user`: 客户信息服务
4. `dubbo-service-search`：搜索服务

## gateway结构说明

1. `GlobalJWTFilter`: 自定义全局JWT过滤器
2. `GlobalLimitFilterGatewayFilterFactory`: 自定义限流过滤器

## task结构说明
1. `dubbo-task-elastic`：使用的是基于elastic job为框架

## 如需之前版本，请关注tag标签，重新拉取tag代码

## 开源共建
1. 如有问题可以提交[issue](https://github.com/ZhiQinIsZhen/dubbo-springboot3/issues)
2. 如有需要Spring Cloud，请点击[Spring Cloud](https://github.com/ZhiQinIsZhen/springcloud-demo)
3. 如有需要Springboot2版本，请点击[dubbo-springboot-nacos](https://github.com/ZhiQinIsZhen/dubbo-springboot-nacos)

## License
Dubbo Springboot3 Project is under the MIT License.