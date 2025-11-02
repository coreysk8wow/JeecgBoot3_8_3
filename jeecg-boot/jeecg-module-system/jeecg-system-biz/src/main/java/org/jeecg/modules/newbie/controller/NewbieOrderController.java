package org.jeecg.modules.newbie.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.newbie.model.dto.NewbieOrderListSearchDto;
import org.jeecg.modules.newbie.model.vo.NewbieOrderListVo;
import org.jeecg.modules.newbie.service.INewbieOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newbie/order")
@Slf4j
public class NewbieOrderController {
    @Autowired
    private INewbieOrderService newbieOrderService;

    @Operation(summary = "获取所有订单-分页")
    @PostMapping(value = "/getAllByPage")
    public Result<IPage<NewbieOrderListVo>> getAllOrdersByPage(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestBody NewbieOrderListSearchDto searchDto) {

        final IPage<NewbieOrderListVo> page = new Page<>(pageNo, pageSize);
        Result<IPage<NewbieOrderListVo>> result = new Result<>();
        try {
            IPage<NewbieOrderListVo> allByPage =
                    newbieOrderService.getAllOrders(page, searchDto);

            result.setSuccess(true);
            result.setResult(allByPage);
            return result;
        } catch (Exception e) {
            log.error("Error fetching categories by page", e);
            result.setSuccess(false);
            result.setMessage("Error fetching categories: " + e.getMessage());
            return result;
        }
    }
}
