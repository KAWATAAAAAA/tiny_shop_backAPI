package cn.wuyuwei.tiny_shop.dao;

import cn.wuyuwei.tiny_shop.entity.SalerDataCenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author wuyuwei
 */
@Repository
@Mapper
public interface SalerDataCenterMapper extends BaseMapper<SalerDataCenter> {

    @Select("SELECT sum(goods_num) \n" +
            "from saler_data_center\n" +
            "where store_id = #{storeId}")
    public int selectSalesVolumeSum(Long storeId);
}

