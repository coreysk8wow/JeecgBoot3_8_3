package org.jeecg.modules.newbie.model.vo;

import lombok.Data;

@Data
public class NewbieSampleItemVo {
    // 排序序号
    private Integer ordinal;

    // 品牌ID
//    private String brandId;

    // 品牌名称
    private String brandName;

    // 类别ID
//    private String categoryId;

    // 类别名称
    private String categoryName;

    // 产品ID
//    private String productId;

    // 产品名称
    private String productName;

    // 箱数
    private Integer qtyBox;

    // 张数
    private Integer qtyOrder;
}
