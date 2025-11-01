package org.jeecg.modules.newbie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.newbie.model.entity.NewbieCategory;
import org.jeecg.modules.newbie.service.INewbieCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/newbie/category")
@Slf4j
public class NewbieCategoryController {
    @Autowired
    public INewbieCategoryService newbieCategoryService;

    /**
     * Get All Categoryies By Page
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Operation(summary = "获取品类列表-分页")
    @GetMapping(value = "/getAllByPage")
    public Result<IPage<NewbieCategory>> getAllCategoriesByPage(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {

        Result<IPage<NewbieCategory>> result = new Result<>();
        IPage<NewbieCategory> pageList = null;
        try {
            LambdaQueryWrapper<NewbieCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(NewbieCategory::getIsDeleted, 0);
            lambdaQueryWrapper.orderByAsc(NewbieCategory::getName);
            pageList = newbieCategoryService.page(
                    new Page<>(pageNo, pageSize), lambdaQueryWrapper);
            result.setSuccess(true);
            result.setResult(pageList);
            return result;
        } catch (Exception e) {
            log.error("Error fetching categories by page", e);
            result.setSuccess(false);
            result.setMessage("Error fetching categories: " + e.getMessage());
            return result;
        }
    }

    @Operation(summary = "获取全部品类列表")
    @GetMapping(value = "/getAll")
    public Result<List<NewbieCategory>> getAllCategories() {
        Result<List<NewbieCategory>> result = new Result<>();
        try {
            LambdaQueryWrapper<NewbieCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(NewbieCategory::getIsDeleted, 0);
            lambdaQueryWrapper.orderByAsc(NewbieCategory::getName);
            List<NewbieCategory> list = newbieCategoryService.list(lambdaQueryWrapper);
            result.setSuccess(true);
            result.setResult(list);
            return result;
        } catch (Exception e) {
            log.error("Error fetching categories by page", e);
            result.setSuccess(false);
            result.setMessage("Error fetching categories: " + e.getMessage());
            return result;
        }
    }

    @Operation(summary = "获取品类By ID")
    @GetMapping(value = "/getById/{id}")
    public Result<NewbieCategory> getById(@PathVariable String id) {
        Result<NewbieCategory> result = new Result<>();
        NewbieCategory category = newbieCategoryService.getById(id);
        if (category == null || category.getIsDeleted() == 1) {
            result = result.error500("未找到对应实体");
        } else {
            result.setResult(category);
            result.success("查询成功");
        }

        return result;
    }

    @Operation(summary = "通过id更新品类信息")
    @PutMapping(value = "/updateById")
    public Result<NewbieCategory> updateById(@RequestBody NewbieCategory newbieCategory) {
        Result<NewbieCategory> result = new Result<>();
        NewbieCategory oldCategory = newbieCategoryService.getById(newbieCategory.getId());
        if (oldCategory == null) {
            result = result.error500("未找到对应实体");
        } else {
            boolean isOk = newbieCategoryService.updateById(newbieCategory);
            if (isOk) {
                result.success("更新成功");
            }
        }

        return result;
    }

    @Operation(summary = "通过id删除品类信息")
    @DeleteMapping(value = "/deleteById/{id}")
    public Result<String> deleteById(@PathVariable String id) {
        try {
            NewbieCategory categoryToUpdate = new NewbieCategory();
            categoryToUpdate.setId(id);
            categoryToUpdate.setIsDeleted(1);
            categoryToUpdate.setUpdateTime(new Date());
            newbieCategoryService.updateById(categoryToUpdate);
        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.error("删除失败!");
        }

        return Result.ok("删除成功!");
    }

    @Operation(summary = "添加品类")
    @PostMapping(value = "/addCategory")
    public Result<String> addCategory(@RequestBody NewbieCategory newbieCategory) {
        Result<String> result = new Result<>();
        try {
            newbieCategoryService.save(newbieCategory);
            result.success("添加成功");
        } catch (Exception e) {
            log.error("添加失败", e);
            result.error500("操作失败");
        }
        return result;
    }

}
