package cn.wuyuwei.tiny_shop.config;


import cn.wuyuwei.tiny_shop.config.Interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* 配置全局的拦截器
* 拦截不合法的请求
* */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())    //通过判断是否符合 AuthenticationInterceptor中的条件 决定操作
                .addPathPatterns("/**");                        //用于添加拦截规则，此处表示拦截所有请求
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

}
/*
* `InterceptorRegistry`内的`addInterceptor`需要一个实现`HandlerInterceptor`接口的拦截器实例，
* `addPathPatterns`方法用于设置拦截器的过滤路径规则。
* */