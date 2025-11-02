package org.jeecg.modules.newbie.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@TableName("newbie_request_todo")
@Schema(description="新手村-待办事项表")
public class NewbieTodo {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    /**
     * 关联相应待办请求的ID
     */
    @Schema(description = "关联相应待办请求的ID")
    private String reqId;

    /**
     * 类别: 1: 样品领用申请单， 2: ...
     */
    @Schema(description = "类别。 1: 样品领用申请单， 2: ...")
    private Integer category;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private java.util.Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private java.util.Date updateTime;

    /**
     * 删除状态（0:正常，1:已删除）
     */
    @TableLogic
    @Schema(description = "删除状态（0:正常，1:已删除）")
    private Integer isDeleted;
}
