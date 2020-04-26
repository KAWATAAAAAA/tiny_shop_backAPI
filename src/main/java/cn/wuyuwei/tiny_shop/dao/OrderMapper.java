package cn.wuyuwei.tiny_shop.dao;

import cn.wuyuwei.tiny_shop.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author wuyuwei
 */
@Repository
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {

}
