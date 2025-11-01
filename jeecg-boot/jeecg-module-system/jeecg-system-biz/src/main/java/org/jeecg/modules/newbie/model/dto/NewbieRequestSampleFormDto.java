package org.jeecg.modules.newbie.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewbieRequestSampleFormDto {
    private String title;

    private String requester;

    private String reqDept;

    private Date reqDate;

    private Integer itemCategory;

    private String phone;

    private List<NewbieSampleItemsDto> items;

    private String fileUploadUrl;

    private String personInChargeId;
}
