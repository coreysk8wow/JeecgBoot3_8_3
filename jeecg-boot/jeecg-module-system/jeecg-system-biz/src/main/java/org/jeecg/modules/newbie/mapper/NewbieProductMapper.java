package org.jeecg.modules.newbie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.newbie.entity.NewbieProduct;
import org.jeecg.modules.newbie.vo.NewbieListProductVo;

public interface NewbieProductMapper extends BaseMapper<NewbieProduct> {
    IPage<NewbieListProductVo> selectAll(IPage page);

//    NewbieListProductVo selectById(String id);
}
