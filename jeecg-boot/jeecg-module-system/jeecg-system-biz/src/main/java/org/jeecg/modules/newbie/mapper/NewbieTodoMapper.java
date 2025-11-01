package org.jeecg.modules.newbie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.newbie.model.entity.NewbieTodo;
import org.jeecg.modules.newbie.model.vo.NewbieTodoVo;

public interface NewbieTodoMapper extends BaseMapper<NewbieTodo> {
    IPage<NewbieTodoVo> selectTodos(IPage<NewbieTodoVo> page,
                                    Integer category,
                                    Integer status);
}
