package cn.wuyuwei.tiny_shop;

import cn.wuyuwei.tiny_shop.dao.GoodsMapper;
import cn.wuyuwei.tiny_shop.entity.GoodsInfo;
import cn.wuyuwei.tiny_shop.utils.IKAnalyzerUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Iktest {
    @Resource
    GoodsMapper goodsMapper;

    @Test
    public void ik(){
        String text="醉里挑灯看剑 梦回吹角连营黑色少女成熟防汗不晕染" +
                "亮色粉红唇彩青春靓丽唇膏打底黑色丝绒唇丝绒山茶花哑光亮光不易脱色滋润持久" +
                "水润保湿洁面乳";
        String str = "青春靓丽少女的黑色";

        try {
            QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<GoodsInfo>();
            List<String> list= IKAnalyzerUtil.cut(str);
            list.forEach((item) ->{
                if (item.length() > 1){
                    queryWrapper.like("goods_name",item).or();
                }
            });


            goodsMapper.selectList(queryWrapper);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
