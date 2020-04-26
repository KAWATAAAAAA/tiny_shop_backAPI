package cn.wuyuwei.tiny_shop.service.serviceImple;

import cn.wuyuwei.tiny_shop.dao.OrderMapper;
import cn.wuyuwei.tiny_shop.entity.OrderInfo;
import cn.wuyuwei.tiny_shop.service.OrderService;
import cn.wuyuwei.tiny_shop.utils.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author wuyuwei
 */
@Service
public class OrderServiceImple implements OrderService {

    @Resource
    OrderMapper orderMapper;
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
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<OrderInfo>();
        updateWrapper.eq("order_id",orderInfo.getOrderId());
        orderInfo.setOrderStatus(4);
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
