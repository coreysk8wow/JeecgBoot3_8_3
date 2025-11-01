package org.jeecg.modules.newbie.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class NewbieTodoVo {
    // 请求ID
    private String reqId;

    // 请求标题
    private String reqTitle;

    // 请求申请人
    private String requester;

    // 申请部门
    private String reqDept;

    // 申请日期
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date reqDate;

    // 待办事项类别。1: 样品领用申请单， 2: ...
    private Integer category;

    // 待办事项状态。 审批状态。0:待办, 1:通过, 2:拒绝
    private Integer statusCode;

    private String statusName;
}
