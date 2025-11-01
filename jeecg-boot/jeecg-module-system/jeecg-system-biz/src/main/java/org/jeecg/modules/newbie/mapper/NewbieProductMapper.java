package org.jeecg.modules.newbie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.newbie.model.entity.NewbieProduct;
import org.jeecg.modules.newbie.model.vo.NewbieListProductVo;

public interface NewbieProductMapper extends BaseMapper<NewbieProduct> {
    IPage<NewbieListProductVo> selectAll(IPage page);
}
