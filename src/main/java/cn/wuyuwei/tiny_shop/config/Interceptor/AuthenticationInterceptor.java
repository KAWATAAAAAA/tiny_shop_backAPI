package cn.wuyuwei.tiny_shop.config.Interceptor;


import cn.wuyuwei.tiny_shop.common.ApiException;
import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;

import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.utils.JwtUtils;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 在请求处理之前进行调用（Controller方法调用之前）
 * 基于URL实现的拦截器
 * @return
 * @throws Exception
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
        // 如果拦截下来的不是方法，则直接放过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(LoginRequired.class)) {
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            if (loginRequired.required()) {
                // 执行认证前先判断请求是否带有token
                if (token == null) {
                    System.out.println("无token，请重新登录");
                    throw new ApiException(ApiResultEnum.FAILED_NO_TOKEN); //需要登录
                }

                // 验证 token，是否合法
                try {
                    DecodedJWT jwt =  JwtUtils.verifierToken(token);
                }
                catch (Exception e) {
                    System.out.println("验证失败，签名不匹配");
                    throw new ApiException(ApiResultEnum.SIGN_VERIFI_ERROR); //验证失败，签名不匹配
                }

              /*  // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                User user = userService.findUserById(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }*/

                return true;
            }
        }
        System.out.println("您访问的接口没有任何要求，直接放行了");
        return true;
    }




}
