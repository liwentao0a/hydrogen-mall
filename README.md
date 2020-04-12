# hydrogen-mall

## 简介

本项目是基于springboot和springcloud开发的发布式商城。基于zuul网关实现了访问限流和用户权限校验，以及对跨域请求的处理。基于jwt实现了单点登录，对用户身份令牌进行统一的发放。基于redis实现了缓存管理，并实现了分布式锁对库存进行锁定操作确保库存的安全性。基于rabbitmq实现了用户订单超时未支付进行删除操作。基于feign进行微服务间的通讯。基于mybatis及tkmapper的数据库访问操作。基于logstash与elk进行统一的日志管理。

## 开发信息

开发环境：window10

开发工具：idea2019.1

开发时间：2020.01.20-至今

## 项目相关

**项目名称**：hydrogen-mall（氢商城）

**端口分配**：

8x端口 网页跳转控制

7xxx端口 后台服务处理

8xxx端口 网页api接口

9xxx端口 eureka、前端静态页面等

**详细信息**：

hmall-zuul：网关

hmall-xxx-web：网页api接口

hmall-xxx-service：后台服务处理

hmall-eureka：注册中心

hmall-config：配置中心

hmall-parent：项目依赖管理

hmall-page：前端静态页面

hmall-api：公共api

