package cn.wuyuwei.tiny_shop.entity;


import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Description  
 * @Author  HelloWorld
 * @Date 2020-04-23 
 */

@Data
@Accessors(chain = true)
@TableName (value = "order_info",autoResultMap = true)
public class OrderInfo  implements Serializable {

	private static final long serialVersionUID =  8991116190325066810L;

	@TableId
	@TableField("order_id" )
	private Long orderId;

	@TableField("order_time" )
	private Date orderTime;

	@TableField("order_status" )
	private Integer orderStatus;

	@TableField("order_total_price" )
	private Double orderTotalPrice;

	@TableField("order_remarks" )
	private String orderRemarks;

	@TableField("address_id" )
	private Long addressId;

	@TableField(value = "goods_list" ,typeHandler = JacksonTypeHandler.class)
	private List<GoodsInfo> goodsList;

	@TableField("user_id" )
	private Long userId;


}
