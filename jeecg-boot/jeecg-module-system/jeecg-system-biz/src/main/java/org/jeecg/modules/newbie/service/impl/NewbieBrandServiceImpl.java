package org.jeecg.modules.newbie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.newbie.entity.NewbieBrand;
import org.jeecg.modules.newbie.mapper.NewbieBrandMapper;
import org.jeecg.modules.newbie.service.INewbieBrandService;
import org.springframework.stereotype.Service;

@Service
public class NewbieBrandServiceImpl extends ServiceImpl<NewbieBrandMapper, NewbieBrand>
        implements INewbieBrandService {
}
