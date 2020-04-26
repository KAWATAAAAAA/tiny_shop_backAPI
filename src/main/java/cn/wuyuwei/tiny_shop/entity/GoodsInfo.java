package cn.wuyuwei.tiny_shop.entity;


import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description  
 * @Author  HelloWorld
 * @Date 2020-02-13 
 */

@Data
@Accessors(chain = true)
@TableName (value = "goods_info",autoResultMap = true)
public class GoodsInfo  implements Serializable {

	private static final long serialVersionUID =  4176370440585937333L;

	@TableId
	@TableField("goods_id" )
	private Long goodsId;

	@TableField("goods_name" )
	private String goodsName;

	@TableField("goods_price" )
	private Double goodsPrice;

	@TableField("goods_num" )
	private Long goodsNum;

	@TableField("goods_preview_img" )
	private String goodsPreviewImg;

	@TableField(value = "goods_label",typeHandler = JacksonTypeHandler.class)
	private List<String> goodsLabel;

	@TableField("store_id" )
	private Long storeId;

	@TableField("store_name" )
	private String storeName;

	@TableField("goods_sales_vol")
	private Long goodsSalesVol;
}
