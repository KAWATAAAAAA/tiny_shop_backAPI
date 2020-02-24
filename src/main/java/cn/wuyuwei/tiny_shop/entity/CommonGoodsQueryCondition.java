package cn.wuyuwei.tiny_shop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuyuwei
 */
@Data
@ApiModel(description = "通用查询条件实体")
public class CommonGoodsQueryCondition {

    @ApiModelProperty(value = "店铺ID [可选]",required = false)
    private Long storeId;

    @ApiModelProperty(value = "分页中，指定当前是第几页 [必选]",required = true)
    private int currentPage;

    @ApiModelProperty(value = "分页中，指定一次查多少数据 [必选]",required = true)
    private int size;

    /**排序条件 sale , price-Asc(升序) , price-Desc(降序)**/
    @ApiModelProperty(value = "排序方式 [必选]",required = false,allowableValues ="none,sale,price-Asc,price-Desc")
    private String sort;

    @ApiModelProperty(value = "起价 [可选]",required = false)
    private int minPrice = 0;
    @ApiModelProperty(value = "顶价 [可选]",required = false)
    private int maxPrice = 0;

    @ApiModelProperty(value = "按标签类型筛选 [可选]",required = false)
    private String goodsLabel;

    @ApiModelProperty(value = "按搜索名称返回结果 [可选]",required = false)
    private String goodsName;

}
