package cn.wuyuwei.tiny_shop.entity;

import lombok.Data;

@Data
public class PayParams {
    private Long orderId;
    private Double totalPrice;
    private String orderName;
    private String orderRemarks;
}
