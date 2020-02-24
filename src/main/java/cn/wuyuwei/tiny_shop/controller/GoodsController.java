package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.entity.CommonGoodsQueryCondition;
import cn.wuyuwei.tiny_shop.entity.GoodsInfo;

import cn.wuyuwei.tiny_shop.service.GoodsService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wuyuwei
 */
@RestController

@RequestMapping("/goods")
@Api(tags = "宝贝 Actions Controller")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    private final String SALE = "sale";
    private final String PRICE_ASC = "price-Asc";
    private final String PRICE_DESC = "price-Desc";

    @LoginRequired
    @PutMapping("/goods-info")
    @ApiOperation(value = "插入宝贝信息",notes = "LoginRequired")
    public Result setGoodsInfo(@RequestBody GoodsInfo goods, HttpServletRequest request) throws Exception{
        System.out.println(goods);
        int result = goodsService.doInsertGoodsInfo(goods);

        if (result == 1){
            return Result.ok("恭喜，宝贝上架成功！");
        }else
        {
            return Result.error(ApiResultEnum.ERROR);
        }
    }


    @GetMapping("/goods-list")
    @ApiOperation(value = "分页查询宝贝",notes = "必须指定参数currentPage，size，sort，其它参数一次请求只能出现一个")
    public Result getGoodsList(CommonGoodsQueryCondition condition)throws Exception{
        System.out.println(condition);
        Map<String,Object> map = null;
        if (this.SALE.equals(condition.getSort())){
            System.out.println("按销量排序");
            map = goodsService.doSortBySalesVol(condition);
        }else if (this.PRICE_ASC.equals(condition.getSort())){
            System.out.println("按价格升序");
            map = goodsService.doSortByPrice(condition);
        }else if (this.PRICE_DESC.equals(condition.getSort())){
            System.out.println("按价格降序");
            map = goodsService.doSortByPrice(condition);
        }else if(condition.getMinPrice() !=0 || condition.getMaxPrice()!=0){
            System.out.println("按价格区间筛选");
            map = goodsService.doFilterByPriceRange(condition);
        }else if (condition.getGoodsLabel() != null){
            System.out.println("按标签筛选");
            map = goodsService.doFilterByLabelType(condition);
        }else {
            System.out.println("按正常分页");
            map = goodsService.doLimitQuery(condition);

        }

        return Result.ok(JSON.toJSON(map));
    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索指定名称的宝贝")
    public Result getGoodsByCondition(CommonGoodsQueryCondition condition)throws Exception{
        Map<String,Object> map = null;
        map = goodsService.doSearchByName(condition);
        if (map.size() > 0){
            return Result.ok(map);
        }
            return Result.error(ApiResultEnum.ERROR);
    }
}
