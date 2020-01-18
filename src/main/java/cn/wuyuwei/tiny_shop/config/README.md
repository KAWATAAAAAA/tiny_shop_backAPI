# @Configuration

加上@Config 注解就相当于 Spring 那些繁杂的xml配置的事情给做了，在spring boot应用启动的时候会被自动加载



# Interceptor

在`SpringBoot2.0`及`Spring 5.0`中`WebMvcConfigurerAdapter`已被废弃

````java
public class InterceptorConfig implements WebMvcConfigurerAdapter
````



网上有说改为继承`WebMvcConfigurationSupport`，不过试了下，还是过期的

````java
public class InterceptorConfig extends WebMvcConfigurationSupport
````



改写为：

```java
public class InterceptorConfig implements WebMvcConfigurer
```

