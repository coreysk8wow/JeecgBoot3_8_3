package org.jeecg.modules.newbie.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("newbie_sample_request")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="新手村-样品领用申请单表的DB表实体")
public class NewbieRequestSample {
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "申请人")
    private String requester;

    @TableField("req_dept")
    @Schema(description = "申请部门")
    private String reqDept;

    @TableField("req_date")
    @Schema(description = "申请日期")
    private Date reqDate;

    @TableField("proc_no")
    @Schema(description = "流程编号")
    private String procNo;

    @TableField("item_category")
    @Schema(description = "领用类型。1:样品")
    private Integer itemCategory;

    @Schema(description = "联系电话")
    private String phone;

    @TableField("file_upload_url")
    @Schema(description = "文件URL")
    private String fileUploadUrl;

    @TableField("person_in_charge_id")
    @Schema(description = "领用部门负责人的用户ID")
    private String personInChargeId;

    @Schema(description = "审批状态。0:待办, 1:通过, 2:拒绝")
    private Integer status;

    @Schema(description = "评论")
    private String comment;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private Date createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private Date updateTime;

    @TableField("is_deleted")
    @TableLogic
    @Schema(description = "删除状态（0:正常，1:已删除）")
    private Integer isDeleted;
}
