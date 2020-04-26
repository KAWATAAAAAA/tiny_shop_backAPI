package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.entity.ShoppingCart;
import cn.wuyuwei.tiny_shop.service.ShoppingCartService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author wuyuwei
 */
@RestController
@RequestMapping("/shopping-cart")
@Api(tags = "购物车 Actions Controller")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    @LoginRequired
    @PostMapping("/goods-item")
    @ApiOperation(value = "向购物车中新增一条商品信息",notes = "LoginRequired")
    public Result addShoppingCartGoodsItem(@RequestBody ShoppingCart shoppingCartGoodsInfo, HttpServletRequest request){
        int n = shoppingCartService.todoInsertShoppingCartGoodsItem(shoppingCartGoodsInfo,request);
        return Result.ok(n);
    }

    @LoginRequired
    @GetMapping("/goods-list")
    @ApiOperation(value = "获取购物车中的商品信息列表",notes = "LoginRequired")
    public Result getShoppingCartGoodsList(Long userId){
        List<ShoppingCart> list  = shoppingCartService.todoGetShoppingCartGoodsList(userId);
        return Result.ok(JSON.toJSON(list));
    }

    @LoginRequired
    @PatchMapping("/goods-item")
    @ApiOperation(value = "更新购物车中商品的信息（数量）",notes = "LoginRequired")
    @ApiImplicitParams({
            @ApiImplicitParam(name="isDecrement",value="更新类型,是否做减法",required=true,dataType = "Boolean")
    })
    public Result updateShoppingCartGoodsInfo(@RequestBody HashMap<String,Object> map ){

        System.out.println(map.get("userId"));

        int n = shoppingCartService.todoUpdateShoppingCartGoodsItem(Long.valueOf(String.valueOf(map.get("userId"))), Long.valueOf(String.valueOf(map.get("goodsId"))),Boolean.valueOf(String.valueOf(map.get("isDecrement"))));
        return Result.ok(n);
    }
}
