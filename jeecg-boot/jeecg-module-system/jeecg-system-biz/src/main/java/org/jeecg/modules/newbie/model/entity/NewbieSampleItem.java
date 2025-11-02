package org.jeecg.modules.newbie.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("newbie_sample_items")
@Schema(description="新手村-样品清单表实体")
public class NewbieSampleItem {
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    @TableField("req_id")
    @Schema(description = "申请表ID")
    private String reqId;

    @TableField("ordinal")
    @Schema(description = "排序序号")
    private Integer ordinal;

    @TableField("brand_id")
    @Schema(description = "品牌ID")
    private String brandId;

    @TableField("category_id")
    @Schema(description = "类别ID")
    private String categoryId;

    @TableField("product_id")
    @Schema(description = "产品ID")
    private String productId;

    @TableField("qty_box")
    @Schema(description = "箱数")
    private Integer qtyBox;

    @TableField("qty_order")
    @Schema(description = "张数")
    private Integer qtyOrder;

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
