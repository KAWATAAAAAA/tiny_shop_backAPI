package cn.wuyuwei.tiny_shop;

import cn.wuyuwei.tiny_shop.dao.UserMapper;
import cn.wuyuwei.tiny_shop.entity.UserInfo;
import cn.wuyuwei.tiny_shop.utils.HostUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void getport(){
        HostUtils hostUtils = new HostUtils() ;

    }
    @Test
    public void uuid(){
        Integer  id = UUID.randomUUID().toString().replace("-","").hashCode();
        System.out.println(id);
        System.out.println(id < 0? -id:id);
    }

    @Test
    public void seOne(){
        UserInfo user = null;
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>();
        queryWrapper.eq("user_id","412036494");

        System.out.println( userMapper.selectOne(queryWrapper));
    }
}
