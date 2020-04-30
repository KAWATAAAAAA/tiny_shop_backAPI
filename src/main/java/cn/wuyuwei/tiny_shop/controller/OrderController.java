package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.entity.OrderInfo;
import cn.wuyuwei.tiny_shop.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wuyuwei
 */
@RestController

@RequestMapping("/order")
@Api(tags = "订单 Actions Controller")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @LoginRequired
    @PostMapping("/stage-one")
    @ApiOperation(value = "创建订单",notes = "LoginRequired")
    public Result createOrder(@RequestBody OrderInfo orderInfo, HttpServletRequest request){

        System.out.println(orderInfo);
        try {
            OrderInfo order = orderService.todoSetOrderStageOne(orderInfo,request);
            return Result.ok(order);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ApiResultEnum.ERROR);
        }
    }

    @LoginRequired
    @PatchMapping("/stage-two")
    @ApiOperation(value = "填写订单附加信息",notes = "LoginRequired")
    public Result updateOrderInfo(@RequestBody OrderInfo orderInfo){

        System.out.println(orderInfo);
        try {
            int n = orderService.todoUpdateOrderStageTwo(orderInfo);
            return Result.ok(n);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ApiResultEnum.ERROR);
        }
    }

    @LoginRequired
    @PatchMapping("/stage-four")
    @ApiOperation(value = "完成支付，更新订单状态为待发货",notes = "LoginRequired")
    public Result updateOrderStatus(@RequestBody OrderInfo orderInfo){

        try {
            int n = orderService.todoUpdateOrderStageFour(orderInfo);
            return Result.ok(n);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ApiResultEnum.ERROR);
        }
    }
    @LoginRequired
    @GetMapping("/order-history")
    @ApiOperation(value = "获取历史订单（订单列表）",notes = "LoginRequired")
    public Result getOrderList(HttpServletRequest request){
        try {
            List<OrderInfo> orderInfoList = orderService.todoSeletOrderList(request);
            return Result.ok(orderInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ApiResultEnum.ERROR);
        }
    }

    @LoginRequired
    @PostMapping("/order-history")
    @ApiOperation(value = "删除单条订单信息",notes = "LoginRequired")
    public Result deleteOrderItem(@RequestParam("orderId") Long orderId){
        try {
            int n = orderService.todoDeleteOrderItem(orderId);
            return Result.ok(n);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ApiResultEnum.ERROR);
        }


    }

    @LoginRequired
    @PatchMapping("/stage-seven")
    @ApiOperation(value = "完成收货，更新订单状态为已完成",notes = "LoginRequired")
    public Result updateOrderStatusComplete(@RequestBody OrderInfo orderInfo){

        try {
            int n = orderService.todoUpdateOrderStageSeven(orderInfo);
            return Result.ok(n);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ApiResultEnum.ERROR);
        }
    }
}
