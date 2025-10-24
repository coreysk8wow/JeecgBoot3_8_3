package org.jeecg.modules.newbie.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.newbie.entity.NewbieProduct;
import org.jeecg.modules.newbie.service.INewbieProductService;
import org.jeecg.modules.newbie.vo.NewbieListProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/newbie/product")
@Slf4j
public class NewbieProductController {
    @Autowired
    private INewbieProductService newbieProductService;

    @Operation(summary = "获取品类列表-分页")
    @GetMapping(value = "/getAllByPage")
    public Result<IPage<NewbieListProductVo>> getAllCategoriesByPage(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {

        Result<IPage<NewbieListProductVo>> result = new Result<>();
        try {
            IPage<NewbieListProductVo> allByPage =
                    newbieProductService.getAllByPage(pageNo, pageSize);
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

/*    @Operation(summary = "获取产品By ID")
    @GetMapping(value = "/getById/{id}")
    public Result<NewbieListProductVo> getById(@PathVariable String id) {
        Result<NewbieListProductVo> result = new Result<>();
        try {
            NewbieListProductVo byId = newbieProductService.getById(id);
            result.success("Success");
            result.setResult(byId);
            return result;
        } catch (Exception e) {
            log.error("Error fetching categories by page", e);
            result.setSuccess(false);
            result.setMessage("Error fetching categories: " + e.getMessage());
            return result;
        }
    }*/

    @Operation(summary = "更新产品By ID")
    @PutMapping(value = "/updateById")
    public Result<String> updateById(@RequestBody NewbieProduct product) {
        Result<String> result = new Result<>();
        try {
            boolean update = newbieProductService.updateById(product);
            if (update) {
                result.success("Update successful");
            } else {
                result.error500("Update failed");
            }
            return result;
        } catch (Exception e) {
            log.error("Error updating product", e);
            result.setSuccess(false);
            result.setMessage("Error updating product: " + e.getMessage());
            return result;
        }
    }

    @Operation(summary = "添加新产品")
    @PostMapping(value = "/addProduct")
    public Result<String> addProduct(@RequestBody NewbieProduct product) {
        Result<String> result = new Result<>();
        try {
            boolean save = newbieProductService.save(product);
            if (save) {
                result.success("Add successful");
            } else {
                result.error500("Add failed");
            }
            return result;
        } catch (Exception e) {
            log.error("Error adding product", e);
            result.setSuccess(false);
            result.setMessage("Error adding product: " + e.getMessage());
            return result;
        }
    }

    @Operation(summary = "通过id删除产品信息")
    @DeleteMapping(value = "/deleteById/{id}")
    public Result<String> deleteById(@PathVariable String id) {
        try {
            NewbieProduct product = new NewbieProduct();
            product.setId(id);
            product.setIsDeleted(1);
            product.setUpdateTime(new Date());
            newbieProductService.updateById(product);
        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.error("删除失败!");
        }

        return Result.ok("删除成功!");
    }
}
