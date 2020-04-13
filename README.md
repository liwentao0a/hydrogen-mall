# hydrogen-mall

## 简介

本项目是基于springboot和springcloud开发的发布式商城。基于zuul网关实现了访问限流和用户权限校验，以及对跨域请求的处理。基于jwt实现了单点登录，对用户身份令牌进行统一的发放。基于redis实现了缓存管理，并实现了分布式锁对库存进行锁定操作确保库存的安全性。基于rabbitmq实现了用户订单超时未支付进行删除操作。基于feign进行微服务间的通讯。基于mybatis及tkmapper的数据库访问操作。基于logstash与elk进行统一的日志管理。

## 开发信息

开发环境：window10

开发工具：idea2019.1

开发时间：2020.01-至今

## 项目相关

**项目名称：**hydrogen-mall（氢商城）

**端口分配：**

8x端口 网页跳转控制

7xxx端口 后台服务处理

8xxx端口 网页api接口

9xxx端口 eureka、前端静态页面等

**详细信息：**

hmall-zuul：网关

hmall-xxx-web：网页api接口

hmall-xxx-service：后台服务处理

hmall-eureka：注册中心

hmall-config：配置中心

hmall-parent：项目依赖管理

hmall-page：前端静态页面

hmall-api：公共api

**难点：**

1. 用户令牌的安全性

   从用户请求中获取盐值，再加上服务端密钥，再进行加密生成令牌密钥，再通过令牌密钥对jwt进行加密。再将token添加到redis中

2. springboot cache包中不存在对redis模糊删除操作

   使用keys进行模糊查询key会影响redis的性能，因此采用scan进行key的模糊查询，然后再进行删除操作。再通过aop注解的方式，减少对业务的耦合性

3. 静态页面结合js进行动态渲染

   直接使用字符串拼接的方式过于繁复，也不利于修改。因此采用vue进行数据的动态绑定，将页面渲染与数据获取进行分离

4. 分布式状态下库存存取的安全性

   通过redis与lua脚本结合设置分布式锁进行解决。每个锁都必须设置超时时间，防止因网络异常导致锁无法释放。每个锁都会生成一个唯一标识，用于防止前一个锁超时后误释放后一个锁

5. 确保微服务间调用的安全性，并进行权限验证

   通过网关对外进行接口的开放，利用防火墙、安全组等限制其他微服务只能由指定服务器进行访问。并由网关对请求的权限进行验证

6. 确保eureka的安全性

   通过springcloud security设置eureka访问的用户名密码，然后关闭springcloud security的跨域访问限制，

   再利用防火墙、安全组等限制只能由指定服务器进行访问