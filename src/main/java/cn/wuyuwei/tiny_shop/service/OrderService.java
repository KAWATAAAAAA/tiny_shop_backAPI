package cn.wuyuwei.tiny_shop.service;

import cn.wuyuwei.tiny_shop.entity.OrderInfo;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wuyuwei
 */
public interface OrderService {
    public OrderInfo todoSetOrderStageOne(OrderInfo orderInfo, HttpServletRequest request)throws Exception;
    public int todoUpdateOrderStageTwo(OrderInfo orderInfo)throws Exception;
    public void todoUpdateOrderStageThree(OrderInfo orderInfo)throws Exception;
    public int todoUpdateOrderStageFour(OrderInfo orderInfo)throws Exception;
    public void todoUpdateOrderFailureStatus(OrderInfo orderInfo);
    public List<OrderInfo> todoSeletOrderList(HttpServletRequest request) throws Exception;
    public int todoDeleteOrderItem(Long orderId) throws Exception;
}
