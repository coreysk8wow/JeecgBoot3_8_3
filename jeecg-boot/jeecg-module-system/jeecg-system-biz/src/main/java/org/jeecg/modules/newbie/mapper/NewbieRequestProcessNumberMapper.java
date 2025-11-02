package org.jeecg.modules.newbie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.newbie.model.entity.NewbieRequestProcessNumber;

public interface NewbieRequestProcessNumberMapper
        extends BaseMapper<NewbieRequestProcessNumber> {

    /**
     * 根据部门和日期查询最大序列号， 并加锁以防止并发问题
     * @param reqDept
     * @param reqDate
     * @return
     */
    Integer seleteMaxSeqByDeptAndDateForUpdate(String reqDept, String reqDate);
}
