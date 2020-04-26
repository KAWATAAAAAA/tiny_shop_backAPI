package cn.wuyuwei.tiny_shop.dao;

import cn.wuyuwei.tiny_shop.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    @Select("select *\n" +
            "from goods_info as g\n" +
            "CROSS join shopping_cart as s\n" +
            "WHERE s.user_id = #{userId} and g.goods_id = s.goods_id")
    List<ShoppingCart> selectShoppingCartGoodsList(Long userId);

    @Update("UPDATE shopping_cart\n" +
            "set num = #{num}\n" +
            "where user_id = #{userId} and goods_id = #{goodsId} ")
    int updateShoppingCartGoodsItem(Long userId,Long goodsId,int num);

    @Delete("DELETE FROM shopping_cart\n" +
            "WHERE user_id = #{userId} and goods_id = #{goodsId}")
    int deletedShoppingCartGoodsItem(Long userId,Long goodsId);

}
