package org.jeecg.modules.newbie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@TableName("newbie_category")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="新手村品类表")
public class NewbieCategory {
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id")
    private String id;

    /**
     * 品类名称
     */
    @Schema(description = "品类名称")
    private String name;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private java.util.Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "修改时间")
    private java.util.Date updateTime;

    @Schema(description = "是否删除, 0未删除 1已删除")
    private Integer isDeleted;
}
