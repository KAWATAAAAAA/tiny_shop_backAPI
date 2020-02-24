package cn.wuyuwei.tiny_shop.config.Interceptor;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wuyuwei
 **/
/**
* -----------------------------分页拦截插件-------------------------------
*
* Mybatis-plus 有默认的分页查询方法，即 selectPage方法
* 但是由于该方法实现的是逻辑分页，即先把符合 queryWrapper 条件的数据全部放在内存中
* 该方法不适用于数据量很大场合，要想实现物理分页，得配置分页插件才能实现物理分页
*
* */
@EnableTransactionManagement
@Configuration
@MapperScan("cn.wuyuwei.tiny_shop.dao.*")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
