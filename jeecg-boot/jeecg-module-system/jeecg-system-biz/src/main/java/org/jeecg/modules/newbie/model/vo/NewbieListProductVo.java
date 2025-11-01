package org.jeecg.modules.newbie.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NewbieListProductVo {
    // newbie_product.id
    private String productId;

    // 产品名称
    private String productName;

    private String brandId;

    // 品牌名称
    private String brandName;

    private String categoryId;

    // 品类名称
    private String categoryName;

    private Date createTime;
}
