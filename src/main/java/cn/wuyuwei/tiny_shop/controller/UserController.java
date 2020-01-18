package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.PassToken;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.entity.RSA256Key;
import cn.wuyuwei.tiny_shop.entity.UserInfo;

import cn.wuyuwei.tiny_shop.entity.dto.UserBaseInfoDTO;
import cn.wuyuwei.tiny_shop.dao.UserMapper;
import cn.wuyuwei.tiny_shop.utils.JwtUtils;
import cn.wuyuwei.tiny_shop.utils.SecretKeyUtils;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")

@Api(tags = "用户控制层")
public class UserController {

    @Autowired
    private UserMapper usermapper;



    @PassToken
    @RequestMapping("/login")
    public Result login(@RequestBody UserInfo user){
        System.out.println(user.getUserPhoneNum());


        // where user_nick_name = "nibaba" and user_real_name = "wqy"
        Map<String,Object> map = new HashMap<>();
        map.put("user_phone_num",user.getUserPhoneNum());
        map.put("user_password",user.getUserPassword());

        List<UserInfo> list = usermapper.selectByMap(map);

        if (list.size() != 0)
        {
            System.out.println(list);
            user = list.get(0); // 存储到 Entity

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

    @LoginRequired
    @RequestMapping("/getPublicKey") //登录成功才能授权公钥
    public Result getPublicKey() throws Exception {
        RSA256Key rsa256Key = SecretKeyUtils.getRSA256Key(); //获取保存了公/私钥的实例
        String data = SecretKeyUtils.getPublicKey(rsa256Key); //再在公私钥对象中获取 公钥
        System.out.println(data);
        return Result.ok(data);
    }

    @LoginRequired
    @RequestMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        UserInfo user = new UserInfo();
        UserBaseInfoDTO baseInfo = new UserBaseInfoDTO();
        // 首先被拦截器拦截，拦截器放到这个方法之后，拦截器中已经验证了token的正确性，在此直接解码使用即可
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token

        DecodedJWT jwt = JWT.decode(token);

        Claim claim = jwt.getClaim("userid");
        String userId = claim.asString();
        System.out.println("------------------------------------------------------------------"+userId);

        QueryWrapper<UserInfo>  queryWrapper = new QueryWrapper<UserInfo>();
        queryWrapper.eq("user_id",userId);
        List<UserInfo> list = usermapper.selectList(queryWrapper);
        user = list.get(0);

        baseInfo.setUserId(user.getUserId());
        baseInfo.setUserPhoneNum(user.getUserPhoneNum());
        baseInfo.setUserEmail(user.getUserEmail());
        baseInfo.setUserNickName(user.getUserNickName());
        baseInfo.setUserRealName(user.getUserRealName());
        baseInfo.setUserBirthday(user.getUserBirthday());
        baseInfo.setDefaultAddress(user.getAddress());


        baseInfo.setOtherAddresses(null);


        return Result.ok(JSON.toJSON(user));
    }

}
