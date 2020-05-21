package cn.wuyuwei.tiny_shop.service.serviceImple;

import cn.wuyuwei.tiny_shop.dao.GoodsMapper;
import cn.wuyuwei.tiny_shop.dao.OrderMapper;
import cn.wuyuwei.tiny_shop.dao.SalerDataCenterMapper;
import cn.wuyuwei.tiny_shop.entity.GoodsInfo;
import cn.wuyuwei.tiny_shop.entity.OrderInfo;
import cn.wuyuwei.tiny_shop.entity.SalerDataCenter;
import cn.wuyuwei.tiny_shop.service.OrderService;
import cn.wuyuwei.tiny_shop.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author wuyuwei
 */
@Service
public class OrderServiceImple implements OrderService {

    @Resource
    OrderMapper orderMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    SalerDataCenterMapper salerDataCenterMapper;
    @Autowired
    UserServiceImple userServiceImple;

    /**
     * 1 使订单状态为 等待确认
     * 创建订单
     * */
    @Override
    public OrderInfo todoSetOrderStageOne(OrderInfo orderInfo, HttpServletRequest request) throws Exception{
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
        String userId = userServiceImple.getUserIdByToken(token);

        int id = UUID.randomUUID().toString().replace("-","").hashCode();
        Long ID = new Long((long)id < 0? -id:id);

        orderInfo.setUserId(Long.parseLong(userId));
        orderInfo.setOrderId(ID);
        orderInfo.setOrderStatus(1);
        orderInfo.setOrderTime(DateUtils.getNowbySimple());

        orderMapper.insert(orderInfo);
        return orderInfo;
    }

    /**
     * 2 使订单状态为 等待确认
     * */
    @Override
    public int todoUpdateOrderStageTwo(OrderInfo orderInfo) throws Exception {
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<OrderInfo>();
        updateWrapper.eq("order_id",orderInfo.getOrderId());
        orderInfo.setOrderStatus(2);
        return orderMapper.update(orderInfo,updateWrapper);

    }

    /**
     * 3 使订单状态为 未支付
     * */
    @Override
    public void todoUpdateOrderStageThree(OrderInfo orderInfo) throws Exception {
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<OrderInfo>();
        updateWrapper.eq("order_id",orderInfo.getOrderId());
        orderInfo.setOrderStatus(3);
        orderMapper.update(orderInfo,updateWrapper);
    }
    /**
     * 4 使订单状态为 待发货
     * */
    @Override
    public int todoUpdateOrderStageFour(OrderInfo orderInfo) throws Exception {
        /*更新订单状态*/
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<OrderInfo>();
        updateWrapper.eq("order_id",orderInfo.getOrderId());
        orderInfo.setOrderStatus(4);
        int n =  orderMapper.update(orderInfo,updateWrapper);

        /*修改店铺的销量*/
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>();
        queryWrapper.select("goods_list").eq("order_id",orderInfo.getOrderId());
        OrderInfo order = orderMapper.selectOne(queryWrapper);
        List<GoodsInfo> list = JSON.parseArray(JSON.toJSONString(order.getGoodsList()), GoodsInfo.class);

        // 记录当前批量操作时间
        Date date = DateUtils.getNowbySimple();
        // 存储上一次的 店铺id
        Long lastId = Long.parseLong("0");

        /*遍历 goodsList ，通过id 组更新销量(附加操作)*/
        for(GoodsInfo item:list)
        {
            // 用每一个goods id去更新对应的销量 +1
            QueryWrapper<GoodsInfo> queryWrapper1 = new QueryWrapper<GoodsInfo>();
            queryWrapper1.select("goods_sales_vol").eq("goods_id",item.getGoodsId());
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setGoodsSalesVol(goodsMapper.selectOne(queryWrapper1).getGoodsSalesVol() + 1);
            goodsInfo.setGoodsId(item.getGoodsId());
            goodsMapper.updateById(goodsInfo);

            // 用每一个store_id去插入一条数据到数据中心

            Long currentId = item.getStoreId();
            // 若当前的 id 与 上一次的 id 不相等
            if (!(currentId.equals(lastId)) )
            {
                System.out.println("---------------------------不相等，插入");
                System.out.println(lastId);
                System.out.println(currentId);
                // 一个store id 只允许做一次插入，需要修改
                SalerDataCenter salerDataCenter = new SalerDataCenter();
                Double totalPrice = 0.0;
                for (GoodsInfo goods:list)
                {
                    if (goods.getStoreId().equals(currentId))
                    {
                        totalPrice+= goods.getGoodsPrice() * goods.getNum();
                    }
                }
                salerDataCenter.setAmount(totalPrice);
                salerDataCenter.setCompletedTime(date);

                int num = 0;

                for (GoodsInfo goods:list)
                {
                    if (goods.getStoreId().equals(currentId))
                    {
                        num++;
                    }
                }

                salerDataCenter.setGoodsNum(num);
                salerDataCenter.setStoreId(item.getStoreId());

                System.out.println(salerDataCenterMapper.insert(salerDataCenter));

                lastId = currentId;
            }
        }
        /*list.forEach(item ->{


        });*/
        return n;
    }
    /**
     * 7 使订单状态为 已完成
     * */
    @Override
    public int todoUpdateOrderStageSeven(OrderInfo orderInfo) throws Exception {
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<OrderInfo>();
        updateWrapper.eq("order_id",orderInfo.getOrderId());
        orderInfo.setOrderStatus(7);
        return orderMapper.update(orderInfo,updateWrapper);

    }

    /**
     * 0 使订单状态为 失败
     * */
    @Override
    public void todoUpdateOrderFailureStatus(OrderInfo orderInfo) {
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<OrderInfo>();
        updateWrapper.eq("order_id",orderInfo.getOrderId());
        orderInfo.setOrderStatus(0);
        orderMapper.update(orderInfo,updateWrapper);
    }

    /**
     * 查询当前用户的所有订单
     * */
    @Override
    public List<OrderInfo> todoSeletOrderList(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
        String userId = userServiceImple.getUserIdByToken(token);

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>();
        queryWrapper.eq("user_id",userId);
        List<OrderInfo> list = orderMapper.selectList(queryWrapper);
        System.out.println(list);
        return list;
    }

    @Override
    public int todoDeleteOrderItem(Long orderId) throws Exception {
        return orderMapper.deleteById(orderId);
    }
}
