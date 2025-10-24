package org.jeecg.modules.newbie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.newbie.entity.NewbieCategory;
import org.jeecg.modules.newbie.mapper.NewbieCategoryMapper;
import org.jeecg.modules.newbie.service.INewbieCategoryService;
import org.springframework.stereotype.Service;

@Service
public class NewbieCategoryServiceImpl extends ServiceImpl<NewbieCategoryMapper, NewbieCategory>
        implements INewbieCategoryService {
}
