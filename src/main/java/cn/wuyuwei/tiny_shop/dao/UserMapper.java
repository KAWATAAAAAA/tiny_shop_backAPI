package cn.wuyuwei.tiny_shop.dao;

import cn.wuyuwei.tiny_shop.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

}
