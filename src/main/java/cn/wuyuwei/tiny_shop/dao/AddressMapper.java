package cn.wuyuwei.tiny_shop.dao;

import cn.wuyuwei.tiny_shop.entity.AddressInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AddressMapper extends BaseMapper<AddressInfo> {

}
