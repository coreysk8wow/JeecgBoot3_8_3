package org.jeecg.modules.newbie.model.dto;

import lombok.Data;

@Data
public class NewbieSampleItemsDto {
    private Integer ordinal;

    private String brandId;

    private String categoryId;

    private String productId;

    private Integer qtyBox;

    private Integer qtyOrder;
}
