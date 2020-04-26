


package cn.wuyuwei.tiny_shop.entity;


import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description  
 * @Author  HelloWorld
 * @Date 2020-01-01 
 */

@Data
@TableName ("address_info" )
public class AddressInfo  implements Serializable {

	private static final long serialVersionUID =  8861352993704061248L;

	@TableId
	@TableField("address_id" )
	private Long addressId;

	@TableField("is_default" )
	private Boolean isDefault;

	@TableField("detail_address" )
	private String detailAddress;

	@TableField("user_id" ) // Foreign key
	private Long userId;

	@TableField("user_real_name" )
	private String userRealName;

	@TableField("user_phone_num" )
	private String userPhoneNum;

}
