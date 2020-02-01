package cn.wuyuwei.tiny_shop.service;


import cn.wuyuwei.tiny_shop.dao.UserMapper;
import cn.wuyuwei.tiny_shop.entity.UserInfo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public UserInfo doLogin(UserInfo user){
        Map<String,Object> map = new HashMap<>();
        map.put("user_phone_num",user.getUserPhoneNum());
        map.put("user_password",user.getUserPassword());

        // where user_nick_name = "nibaba" and user_real_name = "wqy"
        List<UserInfo> list = userMapper.selectByMap(map);

        if (list.size() != 0)
        {
            System.out.println(list);
            user = list.get(0); // 存储到 Entity
        }
        return user;
    }
    public Map doGetBaseInfo(HttpServletRequest request){
        UserInfo user = new UserInfo();
        Map<String,Object> baseInfo = new HashMap<String,Object>();
        // 首先被拦截器拦截，拦截器放到这个方法之后，拦截器中已经验证了token的正确性，在此直接解码使用即可
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
        String userId = getUserIdByToken(token);
        System.out.println("查询的用户ID是："+userId);


        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>();
        queryWrapper.eq("user_id",userId);
        List<UserInfo> list = userMapper.selectList(queryWrapper);
        user = list.get(0);

        baseInfo.put("userId",user.getUserId());
        baseInfo.put("userPhoneNum",user.getUserPhoneNum());
        baseInfo.put("userEmail",user.getUserEmail());
        baseInfo.put("userNickName",user.getUserNickName());
        baseInfo.put("userRealName",user.getUserRealName());
        baseInfo.put("userBirthday",user.getUserBirthday());
        baseInfo.put("userAvatar",user.getUserAvatar());
        baseInfo.put("defaultAddress",user.getAddress());
        baseInfo.put("otherAddresses",null);

        return baseInfo;
    }
    public void doUploadAvatar(String token,String filePath,String fileName){

        //userMapper.
        /*
        * 这里要事先知道是哪个用户
        * */
        String userId = getUserIdByToken(token);
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<UserInfo>();
        updateWrapper.eq("user_id",userId);
        UserInfo user = new UserInfo();
        user.setUserAvatar(filePath + "/" + fileName);
        userMapper.update(user,updateWrapper);
    }

    public String getUserIdByToken(String token){
        DecodedJWT jwt = JWT.decode(token);

        Claim claim = jwt.getClaim("userid");

        System.out.println("--------------------************------------------" + claim.asString());
        return claim.asString();
    }
}
