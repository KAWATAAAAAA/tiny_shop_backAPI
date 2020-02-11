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
 * @Date 2020-02-09 
 */

@Data
@TableName ("store_info" )
public class StoreInfo  implements Serializable {

	private static final long serialVersionUID =  1955950568378265918L;

	@TableId
	@TableField("store_id" )
	private Long storeId;

	@TableField("store_name" )
	private String storeName;

	@TableField("store_desc" )
	private String storeDesc;

	@TableField("store_logo" )
	private String storeLogo;

	@TableField("owner_real_name" )
	private String ownerRealName;

	@TableField("id_card_number" )
	private String idCardNumber;

	@TableField("user_id" )
	private Long userId;

	@TableField("open_time" )
	private Date openTime;

}
