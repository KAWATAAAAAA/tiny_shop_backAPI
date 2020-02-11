package cn.wuyuwei.tiny_shop.entity;

import lombok.Data;

@Data
public class Chat {
    // 消息的目标用户
    private String to;
    // 消息的来源用户
    private String from;
    // 消息的主体内容
    private String content;
}
