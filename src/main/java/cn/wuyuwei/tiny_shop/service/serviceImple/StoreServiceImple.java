package cn.wuyuwei.tiny_shop.service.serviceImple;

import cn.wuyuwei.tiny_shop.dao.StoreMapper;
import cn.wuyuwei.tiny_shop.dao.UserMapper;
import cn.wuyuwei.tiny_shop.entity.StoreInfo;
import cn.wuyuwei.tiny_shop.entity.UserInfo;
import cn.wuyuwei.tiny_shop.utils.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class StoreServiceImple {
    @Resource
    private StoreMapper storeMapper;
    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserServiceImple userService;

    public int doInsertStoreInfo(StoreInfo store, HttpServletRequest request){


        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
        String userId = userService.getUserIdByToken(token);

        store.setStoreId(Long.parseLong(userId));
        store.setUserId(Long.parseLong(userId));
        store.setOpenTime(DateUtils.getNowbySimple());
        int n = storeMapper.insert(store);

        UserInfo user = new UserInfo();
        user.setIsSaler(true);
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<UserInfo>();
        updateWrapper.eq("user_id",userId);
        userMapper.update(user,updateWrapper);
        return n;


    }

    public StoreInfo doGetStoreInfo(String id){
        QueryWrapper<StoreInfo> queryWrapper = new QueryWrapper<StoreInfo>();

        queryWrapper.eq("store_id",id);
        List<StoreInfo> list = storeMapper.selectList(queryWrapper);

        StoreInfo store = new StoreInfo();
        store = list.get(0);

        return store;
    }

}
