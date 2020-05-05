package cn.wuyuwei.tiny_shop.service.serviceImple;

import cn.wuyuwei.tiny_shop.dao.SalerDataCenterMapper;
import cn.wuyuwei.tiny_shop.entity.SalerDataCenter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataCenterServiceImple {

    @Resource
    SalerDataCenterMapper salerDataCenterMapper;

    public List<SalerDataCenter> todoSelectMonthSaleData(Long storeId){

        QueryWrapper<SalerDataCenter> queryWrapper = new QueryWrapper<SalerDataCenter>();
        queryWrapper.eq("store_id",storeId);
        List<SalerDataCenter> list = salerDataCenterMapper.selectList(queryWrapper);

        return list;

    }

    public int selectSumSalesVolume(Long storeId){
        int sum =  salerDataCenterMapper.selectSalesVolumeSum(storeId);
        return sum;
    }
}
