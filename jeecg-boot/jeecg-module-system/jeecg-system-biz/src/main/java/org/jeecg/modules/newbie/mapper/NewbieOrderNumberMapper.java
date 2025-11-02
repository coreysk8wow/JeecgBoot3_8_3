package org.jeecg.modules.newbie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.newbie.model.entity.NewbieOrderNumber;

public interface NewbieOrderNumberMapper extends BaseMapper<NewbieOrderNumber> {
    /**
     * 查询最新的一条订单号记录， 并加锁以防止并发问题
     *
     * @return seq
     */
    Integer selectLatestOrderNumberForUpdate(String orderDate);
}
