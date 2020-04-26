package cn.wuyuwei.tiny_shop.service;

import cn.wuyuwei.tiny_shop.dao.AddressMapper;
import cn.wuyuwei.tiny_shop.entity.AddressInfo;
import cn.wuyuwei.tiny_shop.service.serviceImple.UserServiceImple;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImple implements AddressService{

    @Resource
    AddressMapper addressMapper;

    @Autowired
    UserServiceImple userServiceImple;
    @Override
    public List<AddressInfo> todoSelectAddressInfoList(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userId = userServiceImple.getUserIdByToken(token);

        QueryWrapper<AddressInfo> queryWrapper = new QueryWrapper<AddressInfo>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("is_default");
        List<AddressInfo> addressList = addressMapper.selectList(queryWrapper);

        return addressList;
    }

    @Override
    public AddressInfo todoSelectAddressInfoItem(Long addressId) {
        QueryWrapper<AddressInfo> queryWrapper = new QueryWrapper<AddressInfo>();
        queryWrapper.eq("address_id",addressId);
        return addressMapper.selectOne(queryWrapper);

    }

    @Override
    public int todoUpdateAddressInfoItem(AddressInfo addressInfo) {
        UpdateWrapper<AddressInfo> updateWrapper = new UpdateWrapper<AddressInfo>();
        updateWrapper.eq("address_id",addressInfo.getAddressId());

        return addressMapper.update(addressInfo,updateWrapper);
    }

    @Override
    public int todoInsertAddressInfoItem(AddressInfo addressInfo) {
        /*构造地址 id*/
        int id = UUID.randomUUID().toString().replace("-","").hashCode();
        Long Id = new Long((long)id < 0? -id:id);
        addressInfo.setAddressId(Id);
        /*判断用户是否将地址设为了默认地址*/
        if (addressInfo.getIsDefault())
        {
            /*更新字段 的 is_default 为 0*/
            UpdateWrapper<AddressInfo> updateWrapper = new UpdateWrapper<AddressInfo>();
            updateWrapper.eq("is_default",1);
            AddressInfo addressInfo1 = new AddressInfo();
            addressInfo1.setIsDefault(false);
            addressMapper.update(addressInfo1,updateWrapper);

            return addressMapper.insert(addressInfo);
        }
        else {
            return addressMapper.insert(addressInfo);
        }

    }

}
