package org.jeecg.modules.newbie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.newbie.enums.RequestStatusEnum;
import org.jeecg.modules.newbie.enums.TodoCategoryEnum;
import org.jeecg.modules.newbie.model.entity.NewbieTodo;
import org.jeecg.modules.newbie.model.vo.NewbieTodoVo;

public interface INewbieTodoService extends IService<NewbieTodo> {
    IPage<NewbieTodoVo> getTodosByPage(IPage<NewbieTodoVo> page,
                                       TodoCategoryEnum categoryEnum,
                                       RequestStatusEnum statusEnum);
}
