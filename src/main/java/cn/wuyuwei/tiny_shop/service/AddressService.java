package cn.wuyuwei.tiny_shop.service;

import cn.wuyuwei.tiny_shop.entity.AddressInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AddressService {
    public List<AddressInfo> todoSelectAddressInfoList(HttpServletRequest userId);
    public AddressInfo todoSelectAddressInfoItem(Long addressId);
    public int todoUpdateAddressInfoItem(AddressInfo addressInfo);
    public int todoInsertAddressInfoItem(AddressInfo addressInfo);
}
