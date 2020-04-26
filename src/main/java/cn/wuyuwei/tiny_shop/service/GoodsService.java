package cn.wuyuwei.tiny_shop.service;

import cn.wuyuwei.tiny_shop.entity.CommonGoodsQueryCondition;
import cn.wuyuwei.tiny_shop.entity.GoodsInfo;

import java.util.List;
import java.util.Map;

/**
 * @author wuyuwei
 */
public interface GoodsService {

    public int doInsertGoodsInfo(GoodsInfo goods) throws Exception;
    public Map<String,Object> doSortBySalesVol(CommonGoodsQueryCondition condition)throws Exception;
    public Map<String,Object> doSortByPrice(CommonGoodsQueryCondition condition) throws Exception;
    public Map<String,Object> doFilterByPriceRange(CommonGoodsQueryCondition condition)throws Exception;
    public Map<String,Object> doFilterByLabelType(CommonGoodsQueryCondition condition) throws Exception;
    public Map<String,Object> doLimitQuery(CommonGoodsQueryCondition condition) throws Exception;

    public Map<String,Object> doSearchByName(CommonGoodsQueryCondition condition)throws Exception;

    public List<GoodsInfo> doSelectGoodsInfoGroup(List<String> ids)throws Exception;
}
