# tiny_shop_backAPI
> tiny_shop的服务端



|开发环境/工具|版本|
|---------------|---|
|IntelliJ IDEA |2019.3|
|Mysql|  8.0.13 MySQL Community|

# 已完成的工作

具体接口操作访问 swagger API 文档

- 全局异常捕获
- 全局拦截器验证token
- 通用工具类
  - 整合`privateKey` + `publicKey`秘钥生成工具类
  - 整合时间工具类
  - 整合`Jwt`工具类
  - 整合`ik`分词器工具类
  - 通用的返回类型
- SpringBoot配置
  - 跨域配置
  - Mybatis plus分页拦截器配置
  - 拦截器配置
  - Swagger2  配置
- 整合Swagger2 接口文档

# 接下来要完成的任务

1. websocket 客服聊天

2. 解决 获取的端口号为0 的问题