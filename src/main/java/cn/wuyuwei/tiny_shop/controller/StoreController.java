package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.entity.StoreInfo;
import cn.wuyuwei.tiny_shop.service.serviceImple.StoreServiceImple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController

@RequestMapping("/store")
@Api(tags = "店铺 Actions Controller")
public class StoreController {

    @Autowired
    private StoreServiceImple storeServiceImple;

    @LoginRequired
    @PutMapping("/settled-in")
    @ApiOperation(value = "插入店铺信息",notes = "LoginRequired")
    public Result settledIn(@RequestBody StoreInfo store, HttpServletRequest request){
        int result = storeServiceImple.doInsertStoreInfo(store,request);
        if (result == 0)
        {
            return Result.error(ApiResultEnum.ERROR);
        }
        else
        return Result.ok("入驻成功");
    }

    @GetMapping("/store-info")
    @ApiOperation(value = "获取店铺基本信息" ,notes = "需要传递店铺id")
    public Result getStoreInfo(@RequestParam(value="id",required = false) String id){
        StoreInfo store = storeServiceImple.doGetStoreInfo(id);

        if (store.getStoreId() != Long.parseLong(id))
        {
            return Result.error(ApiResultEnum.ERROR);
        }
            return Result.ok(store);
    }


}
