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
 * @Date 2020-01-02 
 */

@Data
@TableName ("user_info" )
public class UserInfo  implements Serializable {

	private static final long serialVersionUID =  6329896388694028116L;

	@TableId
	@TableField("user_id" )
	private Long userId;

	@TableField("user_phone_num" )
	private String userPhoneNum;

	@TableField("user_nick_name" )
	private String userNickName;

	@TableField("user_real_name" )
	private String userRealName;

	@TableField("user_birthday" )
	private Date userBirthday;

	@TableField("user_email" )
	private String userEmail;

	@TableField("address" )
	private String address;

	@TableField("user_password" )
	private String userPassword;

	@TableField("user_avatar" )
	private String userAvatar;
	
}
