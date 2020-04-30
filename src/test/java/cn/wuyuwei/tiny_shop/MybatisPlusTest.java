package cn.wuyuwei.tiny_shop;

import cn.wuyuwei.tiny_shop.dao.GoodsMapper;
import cn.wuyuwei.tiny_shop.dao.OrderMapper;
import cn.wuyuwei.tiny_shop.dao.SalerDataCenterMapper;
import cn.wuyuwei.tiny_shop.dao.ShoppingCartMapper;
import cn.wuyuwei.tiny_shop.entity.GoodsInfo;
import cn.wuyuwei.tiny_shop.entity.OrderInfo;
import cn.wuyuwei.tiny_shop.entity.SalerDataCenter;
import cn.wuyuwei.tiny_shop.entity.ShoppingCart;
import cn.wuyuwei.tiny_shop.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.internal.matchers.Or;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusTest {

    @Resource
    GoodsMapper goodsMapper;
    @Resource
    ShoppingCartMapper shoppingCartMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    SalerDataCenterMapper salerDataCenterMapper;

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
    @Test
    public void salerDataCenterGroupUpdateTest(){
        // 拿orderId 查出一组 goodsList
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(Long.parseLong("1015831234"));

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>();
        queryWrapper.select("goods_list").eq("order_id",orderInfo.getOrderId());
        OrderInfo order = orderMapper.selectOne(queryWrapper);
        List<GoodsInfo> list = JSON.parseArray(JSON.toJSONString(order.getGoodsList()), GoodsInfo.class);

        // 记录当前批量操作时间
        Date date = DateUtils.getNowbySimple();

        list.forEach(item ->{
            // 用每一个goods id去更新对应的销量 +1
            QueryWrapper<GoodsInfo> queryWrapper1 = new QueryWrapper<GoodsInfo>();
            queryWrapper1.select("goods_sales_vol").eq("goods_id",item.getGoodsId());
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setGoodsSalesVol(goodsMapper.selectOne(queryWrapper1).getGoodsSalesVol() + 1);
            goodsInfo.setGoodsId(item.getGoodsId());
            goodsMapper.updateById(goodsInfo);

            // 用每一个store_id去插入一条数据到数据中心
            SalerDataCenter salerDataCenter = new SalerDataCenter();
            salerDataCenter.setAmount(orderInfo.getOrderTotalPrice());
            salerDataCenter.setCompletedTime(date);
            salerDataCenter.setGoodsNum(list.size());
            salerDataCenter.setStoreId(item.getStoreId());
            salerDataCenterMapper.insert(salerDataCenter);
        });


        System.out.println("-------------------");
        System.out.println(order.getGoodsList());
        // 遍历 goodsList ，通过id 组更新销量

    }
}
