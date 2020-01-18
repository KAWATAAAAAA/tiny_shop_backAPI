package cn.wuyuwei.tiny_shop.entity.dto;

import lombok.Data;

import java.util.Date;
/*
* 用户基本信息 DTO 定义了一套UI界面使用的非敏感数据
* */
@Data
public class UserBaseInfoDTO {
    private Long userId;

    private String userPhoneNum;

    private String userNickName;

    private String userRealName;

    private Date userBirthday;

    private String userEmail;

    private String defaultAddress;

    private String[] otherAddresses;
}
