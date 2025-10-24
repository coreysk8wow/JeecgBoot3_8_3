package org.jeecg.modules.newbie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.newbie.entity.NewbieProduct;
import org.jeecg.modules.newbie.vo.NewbieListProductVo;

public interface INewbieProductService extends IService<NewbieProduct> {
    IPage<NewbieListProductVo> getAllByPage(Integer pageNo, Integer pageSize);

//    NewbieListProductVo getById(String id);
}
