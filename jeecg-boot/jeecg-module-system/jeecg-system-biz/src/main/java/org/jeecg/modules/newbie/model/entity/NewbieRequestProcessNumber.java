package org.jeecg.modules.newbie.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("newbie_request_process_number")
@Schema(description="新手村-申请单-流程号")
public class NewbieRequestProcessNumber {
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id")
    private String id;

    @Schema(description = "申请部门")
    private String reqDept;

    @Schema(description = "申请日期，yyyyMMdd")
    private String reqDate;

    @Schema(description = "三位序数")
    private Integer seq;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @TableLogic
    @Schema(description = "删除状态（0:正常，1:已删除）")
    private Integer isDeleted;
}
