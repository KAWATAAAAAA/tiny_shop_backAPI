package cn.wuyuwei.tiny_shop;

import cn.wuyuwei.tiny_shop.dao.GoodsMapper;
import cn.wuyuwei.tiny_shop.dao.ShoppingCartMapper;
import cn.wuyuwei.tiny_shop.entity.GoodsInfo;
import cn.wuyuwei.tiny_shop.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusTest {

    @Resource
    GoodsMapper goodsMapper;
    @Resource
    ShoppingCartMapper shoppingCartMapper;

    @Test
    public void limitTest(){
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<GoodsInfo>();
        queryWrapper.eq("store_id","1590837172");

        int currentPage = 2; //前端指定  传递 分页的页号
        int size = 8;   // 前端指定     传递  固定值，
        Page<GoodsInfo> page = new Page<GoodsInfo>(currentPage,size);
        IPage<GoodsInfo>iPage =  goodsMapper.selectPage(page,queryWrapper);
        System.out.println("总页数:"+iPage.getPages());
        System.out.println("总记录数:"+iPage.getTotal());
        List<GoodsInfo> list = iPage.getRecords();
        System.out.println(list);
    }
    @Test
    public void shoppingCartQueryTest(){
        //ShoppingCart shoppingCart = shoppingCartMapper.selectShoppingCartGoodsList(1590837172);
        //System.out.println(shoppingCart);
    }

    @Test
    public void  shoppingCartUpdateTest(){
        //int n = shoppingCartMapper.updateShoppingCartGoodsItem(Long.parseLong("3790168"),3);
        //System.out.println(n);
    }
}
