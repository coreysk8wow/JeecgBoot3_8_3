package org.jeecg.modules.newbie.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewbieRequestSampleVo {
    // 标题
    private String title;

    // 申请人
    private String requester;

    // 申请部门,
    private String reqDept;

    // 申请日期
    private Date reqDate;

    // 流程编号
    private String procNo;

    // 领用类型。1:样品
    private Integer itemCategory;

    // 联系电话
    private String phone;

    // 领用样品明细
    private List<NewbieSampleItemVo> items;

    // 文件URL
    private String fileUploadUrl;

    // 领用部门负责人的用户ID
//    private String personInChargeId;

    // 领用部门负责人的姓名
    private String personInChargeName;

    // 审批状态。0:待办, 1:通过, 2:拒绝
    private Integer status;

    // 评论
    private String comment;
}
