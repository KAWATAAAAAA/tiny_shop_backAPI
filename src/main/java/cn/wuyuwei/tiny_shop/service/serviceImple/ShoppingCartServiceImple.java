package cn.wuyuwei.tiny_shop.service.serviceImple;

import cn.wuyuwei.tiny_shop.dao.ShoppingCartMapper;
import cn.wuyuwei.tiny_shop.entity.ShoppingCart;
import cn.wuyuwei.tiny_shop.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ShoppingCartServiceImple implements ShoppingCartService {

    @Resource
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private UserServiceImple userService;

    @Override
    public List<ShoppingCart> todoGetShoppingCartGoodsList(Long userId) {
        List<ShoppingCart> list = shoppingCartMapper.selectShoppingCartGoodsList(userId);
        return list;
    }

    @Override
    public int todoUpdateShoppingCartGoodsItem(Long userId,Long goodsId,Boolean isDecrement) {
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<ShoppingCart>();
        queryWrapper.eq("user_id",userId).eq("goods_id",goodsId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(queryWrapper);
        int n = 0;
        if (isDecrement) {
            if (shoppingCart.getNum() - 1 != 0) {
                n = shoppingCartMapper.updateShoppingCartGoodsItem(userId,goodsId,shoppingCart.getNum() - 1);
            }
            else {
                // 执行删除
                n = this.todoDeletedShoppingCartGoodItem(userId,goodsId);
            }
        }
        else {
            n = shoppingCartMapper.updateShoppingCartGoodsItem(userId,goodsId,shoppingCart.getNum() + 1);
        }
        return n;
    }

    @Override
    public int todoInsertShoppingCartGoodsItem(ShoppingCart shoppingCartGoodsInfo, HttpServletRequest request) {
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
        String userId = userService.getUserIdByToken(token);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setGoodsId(shoppingCartGoodsInfo.getGoodsId());
        shoppingCart.setUserId(Long.parseLong(userId));
        shoppingCart.setNum(1);
            System.out.println(shoppingCart);

        int n = shoppingCartMapper.insert(shoppingCart);
        return n;
    }

    @Override
    public int todoDeletedShoppingCartGoodItem(Long userId,Long goodsId) {
        return shoppingCartMapper.deletedShoppingCartGoodsItem(userId,goodsId);
    }

}
