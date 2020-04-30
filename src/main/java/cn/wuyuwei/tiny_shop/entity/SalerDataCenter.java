package cn.wuyuwei.tiny_shop.entity;


import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * @Description  
 * @Author  HelloWorld
 * @Date 2020-04-29 
 */

@Data
@TableName ("saler_data_center" )
public class SalerDataCenter  implements Serializable {

	private static final long serialVersionUID =  6491441248469997119L;

	@TableId
	@TableField("id" )
	private Long id;

	@TableField("store_id" )
	private Long storeId;

	@TableField("completed_time" )
	private Date completedTime;

	@TableField("goods_num" )
	private int goodsNum;

	@TableField("amount" )
	private Double amount;

}
