package org.jeecg.modules.newbie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.newbie.model.dto.NewbieOrderListSearchDto;
import org.jeecg.modules.newbie.model.entity.NewbieOrderList;
import org.jeecg.modules.newbie.model.entity.NewbieRequestSample;
import org.jeecg.modules.newbie.model.vo.NewbieOrderListVo;

public interface INewbieOrderService extends IService<NewbieOrderList> {
    IPage<NewbieOrderListVo> getAllOrders(
            IPage<NewbieOrderListVo> page,
            NewbieOrderListSearchDto searchDto
    );

    void createOrders(NewbieRequestSample requestSample);
}
