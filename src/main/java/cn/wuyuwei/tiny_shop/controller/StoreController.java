package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.entity.StoreInfo;
import cn.wuyuwei.tiny_shop.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController

@RequestMapping("/store")
@Api(tags = "店铺操作控制层")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @LoginRequired
    @PutMapping("/settled-in")
    @ApiOperation(value = "插入店铺信息",notes = "LoginRequired")
    public Result settledIn(@RequestBody StoreInfo store, HttpServletRequest request){
        int result = storeService.doInsertStoreInfo(store,request);
        if (result == 0)
        {
            return Result.error(ApiResultEnum.ERROR);
        }
        else
        return Result.ok("入驻成功");
    }
}
