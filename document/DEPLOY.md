## 1.项目简介
### 1.1 用户端（无权限控制）
dubbo-api-user + dubbo-service-auth + dubbo-service-user

### 1.2 管理后台（有权限控制）
dubbo-api-admin + dubbo-service-auth + dubbo-service-staff

## 2.项目部署
### 2.1前期准备
#### 2.1.1 Mysql
1. 新建auth库: [auth-server.sql](https://github.com/ZhiQinIsZhen/dubbo-springboot3/blob/main/dubbo-service/dubbo-service-auth/auth-server.sql)
2. 新建user3库: [user.sql](https://github.com/ZhiQinIsZhen/dubbo-springboot3/blob/main/dubbo-service/dubbo-service-user/user.sql)
3. 新建staff3库: [staff.sql](https://github.com/ZhiQinIsZhen/dubbo-springboot3/blob/main/dubbo-service/dubbo-service-staff/staff.sql)、
   [authority.sql](https://github.com/ZhiQinIsZhen/dubbo-springboot3/blob/main/dubbo-service/dubbo-service-staff/authority.sql)
4. 用户名密码需要对照配置文件自行配置成自己的用户名与密码

#### 2.1.2 Redis
1. 用户名密码需要对照配置文件自行配置成自己的用户名与密码

#### 2.1.3 Nacos
1. 新建namespace: liyz
2. 如果不新建该namespace或者使用其他别名，请自行修改配置文件

#### 2.1.4 jdk21
1. 使用idea自行下载jdk21或者Oracle官网下载最新jdk21

### 2.2启动用户端服务
#### 2.2.1. 启动服务: dubbo-service-user
#### 2.2.2. 启动服务: dubbo-service-auth
#### 2.2.3. 启动服务: dubbo-api-user
1. 打开knife4j接口文档: http://127.0.0.1:7072/user/doc.html#/home
2. 注册用户
3. 登录刚刚注册的用户，同时支持手机号码和邮箱登录

### 2.3启动后台端服务
#### 2.2.1. 启动服务: dubbo-service-staff
#### 2.2.2. 启动服务: dubbo-service-auth
#### 2.2.3. 启动服务: dubbo-api-admin
1. 打开knife4j接口文档: http://127.0.0.1:7071/staff/doc.html#/home
2. 注册用户
3. 登录刚刚注册的用户，同时支持手机号码和邮箱登录
4. 对于需要鉴权的接口需要通过接口文档上添加对应的权限

### 2.4启动gateway服务
#### 2.4.1 准备工作（Nacos）
1. 新建namespace: liyz-config
2. 在该namespace下，新增配置：
 Data ID:dubbo-gateway.yaml  
 Group:config
 配置格式:yaml
 新增一行配置:test: 11111

#### 2.4.2 打开接口文档
1. 地址: http://127.0.0.1:8080/doc.html#/home  混合user和staff的所有接口文档，可以下拉切换
2. 地址: http://127.0.0.1:8080/user/doc.html#/home  user端接口文档
3. 地址: http://127.0.0.1:8080/staff/doc.html#/home staff管理后台接口文档
