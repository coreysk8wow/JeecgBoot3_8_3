package org.jeecg.modules.newbie.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NewbieOrderListVo {
    // 订单表ID
    private String id;

    // 流程编号
    private String procNo;

    // 订单号
    private String orderNo;

    // 产品名称
    private String productName;

    // 箱数
    private Integer qtyBox;

    // 申请人
    private String requester;

    // 创建时间
    private Date createTime;
}
