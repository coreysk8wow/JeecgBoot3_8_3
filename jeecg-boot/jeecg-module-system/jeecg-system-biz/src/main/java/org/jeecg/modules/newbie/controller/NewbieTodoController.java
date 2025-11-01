package org.jeecg.modules.newbie.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.newbie.enums.RequestStatusEnum;
import org.jeecg.modules.newbie.enums.TodoCategoryEnum;
import org.jeecg.modules.newbie.model.vo.NewbieTodoVo;
import org.jeecg.modules.newbie.service.INewbieTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newbie/todo")
@Slf4j
public class NewbieTodoController {
    @Autowired
    private INewbieTodoService newbieTodoService;

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param category 待办事项类别，1: 样品领用，2: ...
     * @param status 申请状态，0: 待处理，1: 通过，2: 拒绝
     * @return
     */
    @Operation(summary = "分页查询所有待办事项")
    @GetMapping(value = "/getAllByPage")
    public Result<IPage<NewbieTodoVo>> getAllTodosByPage(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "category", defaultValue = "1") Integer category,
            @RequestParam(name = "status", required = false) Integer status) {

        final IPage<NewbieTodoVo> page = new Page<>(pageNo, pageSize);
        final TodoCategoryEnum todoCategoryEnum = TodoCategoryEnum.fromCode(category);
        final RequestStatusEnum requestStatusEnum = RequestStatusEnum.fromCode(status);

        Result<IPage<NewbieTodoVo>> result = new Result<>();
        try {
            IPage<NewbieTodoVo> todosByPage =
                    newbieTodoService.getTodosByPage(page,
                            todoCategoryEnum,
                            requestStatusEnum);

            result.setSuccess(true);
            result.setResult(todosByPage);
            return result;
        } catch (Exception e) {
            log.error("Error fetching brands by page", e);
            result.error500("Error fetching brands: " + e.getMessage());
            return result;
        }
    }
}