# dubbo-springboot3
=========================================

[![Build Status](https://img.shields.io/badge/Build-ZhiQinlsZhen-red)](https://github.com/ZhiQinIsZhen/dubbo-springboot3)
![Maven](https://img.shields.io/maven-central/v/org.apache.dubbo/dubbo.svg)
![License](https://img.shields.io/github/license/alibaba/dubbo.svg)
![Springboot Version](https://img.shields.io/badge/Springboot-3.1.5-brightgreen)
![SpringJwt Version](https://img.shields.io/badge/SpringJwt-1.1.1.RELEASE-brightgreen)
![jjwt Version](https://img.shields.io/badge/jjwt-0.12.3-brightgreen)
![Dubbo Version](https://img.shields.io/badge/Dubbo-3.3.0(beta1)-brightgreen)
![Mybatis-plus Version](https://img.shields.io/badge/MybatisPlus-3.5.4.1-brightgreen)
![Swagger Version](https://img.shields.io/badge/knife4j-4.3.0-brightgreen)

---

这是一个Springboot3项目，基于Java21、SpringBoot3、Mybatis-plus、Nacos、Dubbo3、Gateway、Security等框架。

---

### 项目结构
1.**dubbo-api**：后置网关层

2.**dubbo-common**：基础包的框架

3.**dubbo-dependencies-bom**：Jar版本管理父pom

4.**dubbo-service**：Dubbo的服务提供者

5.**dubbo-gateway**：前置网关层

---

### api结构说明

---
1.**dubbo-api-admin**: 管理后台网关层，鉴权基于spring-security

2.**dubbo-api-user**: 客户前台网关层

---

### common结构说明

---
1.**dubbo-common-api-starter**: 通用web或者网关层框架

2.**dubbo-common-dao-starter**: 通用DAO层的框架(基于Mybatis-plus)

3.**dubbo-common-lock-starter**: 通用Lock框架

4.**dubbo-common-remote**: 通用Dubbo远程接口框架(包含了参数验证器:validation)

5.**dubbo-common-service**: 业务通用核心框架

6.**dubbo-common-util**: 通用工具类框架

7.**dubbo-exception-filter**: Dubbo自定义异常过滤器

8.**dubbo-security-client-starter**: security-client，适用于各个网关服务中

---

### service结构说明

---
1.**dubbo-service-auth**: 认证资源服务，基于spring-security以及jwt

2.**dubbo-service-staff**: 员工信息服务

3.**dubbo-service-user**: 客户信息服务

---

### gateway结构说明

---
1.**GlobalJWTFilter**: 自定义全局JWT过滤器

2.**GlobalLimitFilterGatewayFilterFactory**: 自定义限流过滤器

---

### 如需之前版本，请关注tag标签，重新拉取tag代码

#### 开源共建
1.如有问题可以提交[issue](https://github.com/ZhiQinIsZhen/dubbo-springboot3/issues)

2.如有需要Spring Cloud，请点击[Spring Cloud](https://github.com/ZhiQinIsZhen/springcloud-demo)

3.如有需要Springboot2版本，请点击[dubbo-springboot-nacos](https://github.com/ZhiQinIsZhen/dubbo-springboot-nacos)