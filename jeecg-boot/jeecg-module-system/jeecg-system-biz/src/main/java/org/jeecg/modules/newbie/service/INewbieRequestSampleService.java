package org.jeecg.modules.newbie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.newbie.model.dto.NewbieRequestSampleFormDto;
import org.jeecg.modules.newbie.model.entity.NewbieRequestSample;
import org.jeecg.modules.newbie.model.vo.NewbieRequestSampleVo;

public interface INewbieRequestSampleService extends IService<NewbieRequestSample> {

    NewbieRequestSample saveForm(NewbieRequestSampleFormDto formDto);

    NewbieRequestSampleVo getRequestById(String reqId);

    void updateRequestStatus(NewbieRequestSample requestSample);
}
