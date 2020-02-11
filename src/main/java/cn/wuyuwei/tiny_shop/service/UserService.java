package cn.wuyuwei.tiny_shop.service;


import cn.wuyuwei.tiny_shop.dao.UserMapper;

import cn.wuyuwei.tiny_shop.entity.UserInfo;
import cn.wuyuwei.tiny_shop.utils.JwtUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public Map doLogin(UserInfo user){
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
        Map<String,Object> baseInfo = new HashMap<String,Object>();
        baseInfo.put("userId",user.getUserId());
        baseInfo.put("userPhoneNum",user.getUserPhoneNum());
        baseInfo.put("userEmail",user.getUserEmail());
        baseInfo.put("userNickName",user.getUserNickName());
        baseInfo.put("userRealName",user.getUserRealName());
        baseInfo.put("userGender",user.getUserGender());
        baseInfo.put("userBirthday",user.getUserBirthday());
        baseInfo.put("userAvatar",user.getUserAvatar());
        baseInfo.put("userIntro",user.getUserIntro());
        baseInfo.put("isSaler",user.getIsSaler());
        baseInfo.put("defaultAddress",user.getAddress());
        baseInfo.put("otherAddresses",null);

        return baseInfo;
    }
    public int doRegister(UserInfo user){
        int id = UUID.randomUUID().toString().replace("-","").hashCode();
        Long Id = new Long((long)id < 0? -id:id);
        user.setUserId(Id);

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>();
        queryWrapper.eq("user_phone_num",user.getUserPhoneNum());

        List<UserInfo>list = userMapper.selectList(queryWrapper);
        int len = list.size();
        if (len > 0){
            return 0;
        }
        return userMapper.insert(user);
    }
    public Map doGetBaseInfo(String id,HttpServletRequest request)throws Exception{
        UserInfo user = new UserInfo();
        Map<String,Object> baseInfo = new HashMap<String,Object>();

        // 首先被拦截器拦截，拦截器放到这个方法之后，拦截器中已经验证了token的正确性，在此直接解码使用即可
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token

        //System.out.println("查询的用户ID是："+userId);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>();
        if (token != null) //token 不为空表示当前用户在登陆状态查，或者是token过期的状态
        {

            if (id == null ) //查当前用户信息
            {
                JwtUtils.verifierToken(token);
                String userId = getUserIdByToken(token);
                queryWrapper.eq("user_id",userId);
            }
            else{   //查其他用户
                queryWrapper.eq("user_id",id);
            }
        }
        else    // 无token，则表示查其他用户信息
        {
            queryWrapper.eq("user_id",id);
        }



        List<UserInfo> list = userMapper.selectList(queryWrapper);
        user = list.get(0);

        baseInfo.put("userId",user.getUserId());
        baseInfo.put("userNickName",user.getUserNickName());
        baseInfo.put("userBirthday",user.getUserBirthday());
        baseInfo.put("userAvatar",user.getUserAvatar());
        baseInfo.put("userGender",user.getUserGender());
        baseInfo.put("userIntro",user.getUserIntro());
        baseInfo.put("isSaler",user.getIsSaler());

        if (id == null){    //敏感信息
            baseInfo.put("userPhoneNum",user.getUserPhoneNum());
            baseInfo.put("userEmail",user.getUserEmail());
            baseInfo.put("userRealName",user.getUserRealName());
            baseInfo.put("defaultAddress",user.getAddress());
            baseInfo.put("otherAddresses",null);

        }

        return baseInfo;
    }
    public int doUpdateUserInfo(UserInfo user,HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String userId = getUserIdByToken(token);
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<UserInfo>();
        updateWrapper.eq("user_id",userId);

        return userMapper.update(user,updateWrapper);
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
