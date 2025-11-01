package org.jeecg.modules.newbie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.newbie.model.entity.NewbieRequestProcessNumber;

public interface NewbieRequestProcessNumberMapper
        extends BaseMapper<NewbieRequestProcessNumber> {

    // lock the row for update
    Integer seleteMaxSeqByDeptAndDateForUpdate(String reqDept, String reqDate);
}
