package cn.wuyuwei.tiny_shop.service;

import cn.wuyuwei.tiny_shop.entity.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wuyuwei
 */
public interface ShoppingCartService {

    /**
     * 获取购物车中商品列表
     * */
    public List<ShoppingCart> todoGetShoppingCartGoodsList(Long userId);

    /**
    * 更新购物车中的某一条商品信息
    * */
    public int todoUpdateShoppingCartGoodsItem(Long userId,Long goodsId,Boolean isDecrement);

    /**
     * 向购物车中新增一条商品信息
     * */
    public int todoInsertShoppingCartGoodsItem(ShoppingCart shoppingCartGoodsInfo, HttpServletRequest request);

    /**
     * 删除购物车中单条数据
     * */
    public int todoDeletedShoppingCartGoodItem(Long userId,Long goodsId);

    /**
     * 删除购物车中所有数据 （暂定）
     * */



}
