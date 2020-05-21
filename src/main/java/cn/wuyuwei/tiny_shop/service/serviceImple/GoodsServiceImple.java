package cn.wuyuwei.tiny_shop.service.serviceImple;

import cn.wuyuwei.tiny_shop.dao.GoodsMapper;
import cn.wuyuwei.tiny_shop.entity.CommonGoodsQueryCondition;
import cn.wuyuwei.tiny_shop.entity.GoodsInfo;
import cn.wuyuwei.tiny_shop.service.GoodsService;
import cn.wuyuwei.tiny_shop.utils.IKAnalyzerUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wuyuwei
 * 该类的查询功能统一做分页处理
 */
@Service
public class GoodsServiceImple implements GoodsService {

    @Resource
    GoodsMapper goodsMapper;

    @Override
    public int doInsertGoodsInfo(GoodsInfo goods) throws Exception{
       // String token = request.getHeader("Authorization");// 从 http 请求头中取出 token

        int id = UUID.randomUUID().toString().replace("-","").hashCode();
        Long Id = new Long((long)id < 0? -id:id);

        // 生成id
        goods.setGoodsId(Id);
        goods.setGoodsSalesVol((long)0);

        return goodsMapper.insert(goods);

    }

    @Override
    public int doUpdateGoodsInfo(GoodsInfo goods) throws Exception {

        /*UpdateWrapper<GoodsInfo> updateWrapper = new UpdateWrapper<GoodsInfo>();
        updateWrapper.eq("goods_id",goods.getGoodsId());*/
        int n = goodsMapper.updateById(goods);
        return n;
    }

    @Override
    public int doDeletedGoodsInfo(GoodsInfo goods) throws Exception {

        int n = goodsMapper.deleteById(goods);
        return n;
    }

    /**
    * 按销量排序
    * */
    @Override
    public Map<String,Object> doSortBySalesVol(CommonGoodsQueryCondition condition)throws Exception{
        QueryWrapper<GoodsInfo> queryWrapper = this.setQueryCondition(condition);
        queryWrapper.orderByDesc("goods_sales_vol");
        return this.getMyGoodsInfo(queryWrapper,condition.getCurrentPage(),condition.getSize());
    }

    /**
    * 按价格升序
    * */
    @Override
    public Map<String,Object> doSortByPrice(CommonGoodsQueryCondition condition)throws Exception{
        QueryWrapper<GoodsInfo> queryWrapper = this.setQueryCondition(condition);
        if (condition.getSort().equals("price-Asc")) {
            queryWrapper.orderByAsc("goods_price");
        } else {
            queryWrapper.orderByDesc("goods_price");
        }
        return this.getMyGoodsInfo(queryWrapper,condition.getCurrentPage(),condition.getSize());
    }

    /**
    * 按价格区间筛选
    * */
    @Override
    public Map<String,Object> doFilterByPriceRange(CommonGoodsQueryCondition condition) throws Exception{
        QueryWrapper<GoodsInfo> queryWrapper = this.setQueryCondition(condition);

        int min = condition.getMinPrice();
        int max = condition.getMaxPrice();
        if (min < max) {
            queryWrapper.ge("goods_price",condition.getMinPrice()).le("goods_price",condition.getMaxPrice());
        } else if (min > 0 && max == 0) {
            queryWrapper.ge("goods_price",condition.getMinPrice());
        } else if (min == 0 && max > 0) {
            queryWrapper.ge("goods_price",condition.getMinPrice()).le("goods_price",condition.getMaxPrice());
        } else if (min == max && (min>0&&max>0)) {
            queryWrapper.eq("goods_price",condition.getMinPrice());
        }
        return this.getMyGoodsInfo(queryWrapper,condition.getCurrentPage(),condition.getSize());
    }

    /**
    * 按标签类型筛选
    * */
    @Override
    public Map<String,Object> doFilterByLabelType(CommonGoodsQueryCondition condition)throws Exception{
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<GoodsInfo>();
        if (condition.getStoreId() != null){
            queryWrapper.eq("store_id",condition.getStoreId());
        }
        queryWrapper.like("goods_label",condition.getGoodsLabel());
        return this.getMyGoodsInfo(queryWrapper,condition.getCurrentPage(),condition.getSize());
    }

    @Override
    public Map<String,Object> doLimitQuery(CommonGoodsQueryCondition condition)throws Exception{
        QueryWrapper<GoodsInfo> queryWrapper = this.setQueryCondition(condition);
        return this.getMyGoodsInfo(queryWrapper,condition.getCurrentPage(),condition.getSize());
    }

    @Override
    public Map<String, Object> doSearchByName(CommonGoodsQueryCondition condition) throws Exception {
        QueryWrapper<GoodsInfo> queryWrapper = this.setQueryCondition(condition);
        return this.getMyGoodsInfo(queryWrapper,condition.getCurrentPage(),condition.getSize());
    }

    @Override
    public  List<GoodsInfo> doSelectGoodsInfoGroup(List<String> goodsIdList) throws Exception {
        List<GoodsInfo> list = goodsMapper.selectBatchIds(goodsIdList);
        return list;
    }


    /**
     *  私有内部公用方法，用于设置查询条件
     **/
    private QueryWrapper<GoodsInfo> setQueryCondition(CommonGoodsQueryCondition condition)throws Exception{
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<GoodsInfo>();

        /**嵌套and 条件**/
        queryWrapper.and( wrapper -> {
            // 判断是否有id，有就代表查找指定店铺
            if (condition.getStoreId() != null){
                wrapper.eq("store_id",condition.getStoreId());
            }
            // 按照名称分词模糊查询
            if (condition.getGoodsName() != null)
            {
                List<String> list= null;
                try {
                    list = IKAnalyzerUtil.cut(condition.getGoodsName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0 ;  i < list.size() ; i++)
                {
                    //去掉单字条件,且最后不主动调用 or() 而是选择默认的 and 条件连接
                    if (i == list.size() - 1 && list.get(i).length() > 1){
                        wrapper.like("goods_name",list.get(i));
                    }
                    // 去掉单字条件
                    else if (list.get(i).length() > 1)
                    {
                        wrapper.like("goods_name",list.get(i)).or();
                    }
                }

            }
            // 按标签查找
            if (condition.getGoodsLabel() != null)
            {
                wrapper.like("goods_label",condition.getGoodsLabel());
            }
        });

        return queryWrapper;
    }

    /**
    * 私有内部公用方法,通用实体返回
    * */
    private Map<String,Object> getMyGoodsInfo(QueryWrapper queryWrapper,int currentPage,int size){

        Page<GoodsInfo> page = new Page<GoodsInfo>(currentPage,size);
        IPage<GoodsInfo> iPage =  goodsMapper.selectPage(page,queryWrapper);
        List<GoodsInfo> list = iPage.getRecords();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("goodsInfo",list);
        map.put("total",iPage.getTotal());
        return map;
    }

    /**
    * 热度商品
    * */
    @Override
    public Map<String,Object> getHotGoodsList(){
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<GoodsInfo>();
        queryWrapper.orderByDesc("goods_sales_vol");
        return this.getMyGoodsInfo(queryWrapper,0,10);
    }
}
