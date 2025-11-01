package org.jeecg.modules.newbie.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.newbie.enums.RequestStatusEnum;
import org.jeecg.modules.newbie.enums.TodoCategoryEnum;
import org.jeecg.modules.newbie.mapper.NewbieTodoMapper;
import org.jeecg.modules.newbie.model.entity.NewbieTodo;
import org.jeecg.modules.newbie.model.vo.NewbieTodoVo;
import org.jeecg.modules.newbie.service.INewbieTodoService;
import org.springframework.stereotype.Service;

@Service
public class NewbieTodoServiceImpl
        extends ServiceImpl<NewbieTodoMapper, NewbieTodo>
        implements INewbieTodoService {

    @Override
    public IPage<NewbieTodoVo> getTodosByPage(IPage<NewbieTodoVo> page,
                                              TodoCategoryEnum categoryEnum,
                                              RequestStatusEnum statusEnum) {

        Integer statusCode = statusEnum != null ? statusEnum.getCode() : null;
        Integer categoryCode = categoryEnum != null ? categoryEnum.getCode() : null;
        IPage<NewbieTodoVo> newbieTodoVoIPage =
                getBaseMapper().selectTodos(
                        page,
                        categoryCode,
                        statusCode
                );

        newbieTodoVoIPage.getRecords().forEach(todoVo -> {
            todoVo.setStatusName(RequestStatusEnum.fromCode(todoVo.getStatusCode()).getName());
        });

        return newbieTodoVoIPage;
    }
}
