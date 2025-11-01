package org.jeecg.modules.newbie.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.newbie.model.dto.NewbieRequestSampleFormDto;
import org.jeecg.modules.newbie.model.entity.NewbieRequestSample;
import org.jeecg.modules.newbie.model.entity.NewbieTodo;
import org.jeecg.modules.newbie.model.vo.NewbieRequestSampleVo;
import org.jeecg.modules.newbie.service.INewbieRequestSampleService;
import org.jeecg.modules.newbie.service.INewbieTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newbie/request/sample")
@Slf4j
public class NewbieRequestSampleController {
    @Autowired
    private INewbieRequestSampleService newbieRequestSampleService;

    @Autowired
    private INewbieTodoService newbieTodoService;

    @Transactional
    @Operation(summary = "保存样品领用申请单")
    @PostMapping(value = "/save")
    public Result<String> submitRequest(
            @RequestBody NewbieRequestSampleFormDto formDto) {
        Result<String> result = new Result<>();
        NewbieRequestSample newbieRequestSample = null;
        try {
            newbieRequestSample = newbieRequestSampleService.saveForm(formDto);
            log.info("样品领用申请单 提交成功，ID：" + newbieRequestSample.getId());
        } catch (Exception e) {
            log.error("Error adding product", e);
            result.error500("样品领用申请表单 提交失败" + e.getMessage());
            return result;
        }

        /*
            保存到newbie_todo表中。

            方法1. 使用消息队列，异步处理，提升接口性能。
                  依靠消息队列的可靠性，保证数据的最终一致性。

            方法2. 在这里不保存待办事项，使用定时任务，5分钟扫描一次newbie_sample_request表，
                  将未创建待办事项的请求，创建对应的待办事项，保存到newbie_todo表中。
                  最终一致。

            方法3. 在这里保存待办事项到newbie_todo表中，使用本地事务，保证数据一致性。
                  最终一致。

            此处为简单期间，采用方法3。
         */
        NewbieTodo todo = new NewbieTodo();
        todo.setReqId(newbieRequestSample.getId());
        todo.setCategory(1);

        try {
            newbieTodoService.save(todo);
            log.info("样品领用申请单 待办事项 创建成功，ID：" + todo.getId());
        } catch (Exception e) {
            log.error("Error adding todo", e);
            result.error500("样品领用申请单 待办事项 创建失败" + e.getMessage());
            return result;
        }

        return result;
    }

    @Operation(summary = "获取样品领用申请单BY REQ ID")
    @GetMapping(value = "/getById/{reqId}")
    public Result<NewbieRequestSampleVo> getRequestById(@PathVariable String reqId) {
        Result<NewbieRequestSampleVo> result = new Result<>();
        try {
            NewbieRequestSampleVo requestSampleVo =
                    newbieRequestSampleService.getRequestById(reqId);

            result.setResult(requestSampleVo);
            result.success("获取样品领用申请单成功");
        } catch (Exception e) {
            log.error("Error fetching request by id", e);
            result.error500("获取样品领用申请单失败" + e.getMessage());
        }

        return result;
    }

    @Transactional
    @Operation(summary = "更新样品领用申请单状态")
    @PutMapping(value = "/updateStatus")
    public Result<String> updateRequestStatus(@RequestBody NewbieRequestSample requestSample) {
        Result<String> result = new Result<>();
        try {
            newbieRequestSampleService.updateRequestStatus(requestSample);
            result.success("更新样品领用申请单状态成功");
        } catch (Exception e) {
            log.error("Error updating request status", e);
            result.error500("更新样品领用申请单状态失败" + e.getMessage());
            return result;
        }

        /*
            审批通过后，生成订单，有以下3种方式保证数据一致性：
                方法1: 消息队列（异步，最终一致）
                方法2: 定时任务扫描（异步，最终一致）
                方法3: 本地事务（同步，全局一致）
            此处为了简单，采用本地事务方式。
         */
        

        return result;
    }


}
