package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.PassToken;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;

import cn.wuyuwei.tiny_shop.entity.RSA256Key;

import cn.wuyuwei.tiny_shop.entity.UserInfo;
import cn.wuyuwei.tiny_shop.service.serviceImple.UserServiceImple;
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

@Api(tags = "用户 Actions Controller")
public class UserController {

    @Autowired
    private UserServiceImple userService;


    @PassToken
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ApiOperation(value = "登录")
    public Result login(@RequestParam("userPhoneNum") String userPhoneNum,@RequestParam("userPassword") String userPassword){
        //取出参数 封装进实体类
        UserInfo user = new UserInfo();
        user.setUserPhoneNum(userPhoneNum);
        user.setUserPassword(userPassword);

        // 开数据库判断用户可用性
        Map usermap = userService.doLogin(user);

        if (usermap.get("userId") != null)
        {
            System.out.println(usermap.get("userId"));
            //开始签发 token
                //usermap.get("userId")
            try {

                String jwt = JwtUtils.generTokenByRS256(usermap.get("userId"));
                usermap.put("token",jwt);
                 return Result.ok(JSON.toJSON(usermap));
            } catch (Exception e) {
                e.printStackTrace();
                //JWTCreationException
            }
        }

        return Result.error("账号或密码错误，请重试");


    }
    @PassToken
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ApiOperation(value = "注册")
    public Result register(@RequestBody UserInfo user){
        int result = userService.doRegister(user);
        if (result == 0){
            return Result.error(ApiResultEnum.HAS_BEEN_REGISTERED);
        }
        return Result.ok(result);
    }

    //@LoginRequired
    @ApiOperation(value = "获取公钥",notes = "用于传输加密信息时先获取公钥，加密后再传输")
    @RequestMapping(value = "/publicKey",method = RequestMethod.GET) //登录成功才能授权公钥
    public Result getPublicKey() throws Exception {
        RSA256Key rsa256Key = SecretKeyUtils.getRSA256Key(); //获取保存了公/私钥的实例
        String data = SecretKeyUtils.getPublicKey(rsa256Key); //再在公私钥对象中获取 公钥
        System.out.println(data);
        return Result.ok(data);
    }

    //@LoginRequired
    @ApiOperation(value = "获取用户信息",notes = "id与token任意有一个即可")
    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    public Result getUserInfo(@RequestParam(value="id",required = false) String id,HttpServletRequest request){


        Map baseInfo = null;
        try {
            baseInfo = userService.doGetBaseInfo(id,request);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ApiResultEnum.TOKEN_EXPIRED);
        }

        return Result.ok(JSON.toJSON(baseInfo));
    }
    @LoginRequired
    @ApiOperation(value = "更新用户信息",notes = "LoginRequired，更新部分信息")
    @RequestMapping(value = "/userInfo",method = RequestMethod.PATCH)
    public Result updateUserInfo(@RequestBody UserInfo user,HttpServletRequest request){

        if (userService.doUpdateUserInfo(user,request) == 1)
        {
            return Result.ok("更新成功");

        }
        else
            return Result.error(ApiResultEnum.ERROR);
    }
}
