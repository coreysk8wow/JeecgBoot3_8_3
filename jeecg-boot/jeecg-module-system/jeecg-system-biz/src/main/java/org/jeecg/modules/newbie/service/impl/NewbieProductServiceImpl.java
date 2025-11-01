package org.jeecg.modules.newbie.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.newbie.model.entity.NewbieProduct;
import org.jeecg.modules.newbie.mapper.NewbieProductMapper;
import org.jeecg.modules.newbie.service.INewbieProductService;
import org.jeecg.modules.newbie.model.vo.NewbieListProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewbieProductServiceImpl extends ServiceImpl<NewbieProductMapper, NewbieProduct>
        implements INewbieProductService {

    @Autowired
    private NewbieProductMapper newbieProductMapper;

    @Override
    public IPage<NewbieListProductVo> getAllByPage(Integer pageNo, Integer pageSize) {
        IPage<NewbieListProductVo> page = new Page<>(pageNo, pageSize);
        IPage<NewbieListProductVo> newbieListProductVoList =
                newbieProductMapper.selectAll(page);

        return newbieListProductVoList;
    }
}
