package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.entity.SalerDataCenter;
import cn.wuyuwei.tiny_shop.service.serviceImple.DataCenterServiceImple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wuyuwei
 */
@RestController
@RequestMapping("/data-center")
@Api(tags = "商户数据中心 Controller")
public class DataCenterController {

    @Autowired
    DataCenterServiceImple dataCenterServiceImple;


    @LoginRequired
    @GetMapping("/base-data")
    @ApiOperation(value = "获取当前店铺一个月时间的销售量与总额",notes = "LoginRequired")
    public Result getMonthSaleData(Long storeId){

        List<SalerDataCenter> list = dataCenterServiceImple.todoSelectMonthSaleData(storeId);
        return Result.ok(list);
    }

}
