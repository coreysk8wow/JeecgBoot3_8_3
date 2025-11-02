package org.jeecg.modules.newbie.model.dto;

import lombok.Data;

/**
 * 订单列表，搜索条件DTO
 */
@Data
public class NewbieOrderListSearchDto {
    // 流程编号
    private String procNo;

    // 订单号
    private String orderNo;

    private String beginDate;

    private String endDate;
}
