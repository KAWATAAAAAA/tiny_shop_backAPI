package cn.wuyuwei.tiny_shop.entity;


import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description  
 * @Author  HelloWorld
 * @Date 2020-03-27 
 */

@Data
@TableName ("shopping_cart" )
public class ShoppingCart implements Serializable{

	private static final long serialVersionUID =  5878367069184620952L;

	@TableId
	@TableField("goods_id" )
	private Long goodsId;

	@TableId
	@TableField("user_id" )
	private Long userId;

	@TableField("num" )
	private int num;

	@TableField(exist = false)
	private String goodsName;

	@TableField(exist = false)
	private Double goodsPrice;

	@TableField(exist = false)
	private Long goodsNum;

	@TableField(exist = false)
	private String goodsPreviewImg;

	@TableField(exist = false)
	private Long storeId;

	@TableField(exist = false)
	private String storeName;


}
