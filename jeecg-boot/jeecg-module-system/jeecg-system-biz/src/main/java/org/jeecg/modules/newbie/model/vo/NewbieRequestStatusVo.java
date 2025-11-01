package org.jeecg.modules.newbie.model.vo;

import lombok.Data;

@Data
public class NewbieRequestStatusVo {
    // 待办事项状态。 审批状态。0:待办, 1:通过, 2:拒绝
    private Integer status;

    private String statusName;
}
