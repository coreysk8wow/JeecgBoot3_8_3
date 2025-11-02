package org.jeecg.modules.newbie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.newbie.model.dto.NewbieOrderListSearchDto;
import org.jeecg.modules.newbie.model.entity.NewbieOrderList;
import org.jeecg.modules.newbie.model.vo.NewbieOrderListVo;

public interface NewbieOrderMapper extends BaseMapper<NewbieOrderList> {
    IPage<NewbieOrderListVo> getAllOrders(
            IPage<NewbieOrderListVo> page,
            NewbieOrderListSearchDto searchDto
    );
}
