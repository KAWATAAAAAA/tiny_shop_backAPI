package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.PassToken;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;

import cn.wuyuwei.tiny_shop.entity.RSA256Key;
import cn.wuyuwei.tiny_shop.entity.UserInfo;

import cn.wuyuwei.tiny_shop.service.UserService;
import cn.wuyuwei.tiny_shop.utils.JwtUtils;
import cn.wuyuwei.tiny_shop.utils.SecretKeyUtils;
import com.alibaba.fastjson.JSON;

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/user")

@Api(tags = "用户操作控制层")
public class UserController {

    @Autowired
    private UserService userService;


    @PassToken
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Result login(@RequestParam("userPhoneNum") String userPhoneNum,@RequestParam("userPassword") String userPassword){
        //取出参数 封装进实体类
        UserInfo user = new UserInfo();
        user.setUserPhoneNum(userPhoneNum);
        user.setUserPassword(userPassword);

        // 开数据库判断用户可用性
        user = userService.doLogin(user);

        if (user.getUserId() != null)
        {
            //开始签发 token
            try {
                return Result.ok(JwtUtils.generTokenByRS256(user.getUserId()));
            } catch (Exception e) {
                e.printStackTrace();
                //JWTCreationException
            }
        }

        return Result.error("账号或密码错误，请重试");


    }

    //@LoginRequired
    @ApiOperation(value = "获取公钥",notes = "用于传输加密信息时先获取公钥，加密后再传输")
    @RequestMapping(value = "/PublicKey",method = RequestMethod.GET) //登录成功才能授权公钥
    public Result getPublicKey() throws Exception {
        RSA256Key rsa256Key = SecretKeyUtils.getRSA256Key(); //获取保存了公/私钥的实例
        String data = SecretKeyUtils.getPublicKey(rsa256Key); //再在公私钥对象中获取 公钥
        System.out.println(data);
        return Result.ok(data);
    }

    @LoginRequired
    @RequestMapping(value = "/UserInfo",method = RequestMethod.GET)
    public Result getUserInfo(HttpServletRequest request){

        Map baseInfo = userService.doGetBaseInfo(request);

        return Result.ok(JSON.toJSON(baseInfo));
    }

}
