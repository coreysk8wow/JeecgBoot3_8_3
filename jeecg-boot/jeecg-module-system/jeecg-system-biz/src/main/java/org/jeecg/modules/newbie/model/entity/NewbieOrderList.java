package org.jeecg.modules.newbie.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("newbie_order")
@Schema(description="新手村-订单表")
public class NewbieOrderList {
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "关联相应请求的ID")
    private String reqId;

    @Schema(description = "关联相应清单列表中记录的ID")
    private String itemId;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @TableLogic
    @Schema(description = "删除状态（0:正常，1:已删除）")
    private Integer isDeleted;
}
