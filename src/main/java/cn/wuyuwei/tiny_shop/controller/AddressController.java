package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.entity.AddressInfo;
import cn.wuyuwei.tiny_shop.service.AddressService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author wuyuwei
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @LoginRequired
    @GetMapping("/address-list")
    @ApiOperation(value = "查询用户的地址信息列表",notes = "LoginRequired")
    public Result queryAddressList(HttpServletRequest request){
        List<AddressInfo> addressList = addressService.todoSelectAddressInfoList(request);

        return Result.ok(JSON.toJSON(addressList));
    }

    @GetMapping("/address-item")
    @ApiOperation(value = "查询用户的地址信息",notes = "LoginRequired")
    public Result queryAddressItem(Long addressId){
        AddressInfo addressInfo = addressService.todoSelectAddressInfoItem(addressId);
        if (addressInfo == null)
        {
            Result r = new Result();
            r.put("data",addressInfo);
            r.put("messaeg","地址列表中不存在该地址，请重新确认");

            return r;
        }
        else {
            return Result.ok(addressInfo);
        }
    }
    @PostMapping("/address-item")
    @ApiOperation(value = "添加一条地址信息",notes = "LoginRequired")
    public Result addAddressItem(@RequestBody AddressInfo addressInfo){

        int n = addressService.todoInsertAddressInfoItem(addressInfo);
        Result r = new Result();

        if (n > 0){
            r.put("data",n);
            r.put("message","添加成功");
            return r;
        }
        else{
            return Result.error(ApiResultEnum.ERROR);
        }

    }

    @PatchMapping("/address-item")
    @ApiOperation(value = "添加一条地址信息",notes = "LoginRequired")
    public Result modifyAddressItem(@RequestBody AddressInfo addressInfo){
        int n = addressService.todoUpdateAddressInfoItem(addressInfo);

        return Result.ok(n);
    }

}
