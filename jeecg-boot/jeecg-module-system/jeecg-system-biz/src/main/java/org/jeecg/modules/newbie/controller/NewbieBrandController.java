package org.jeecg.modules.newbie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.newbie.entity.NewbieBrand;
import org.jeecg.modules.newbie.service.INewbieBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/newbie/brand")
@Slf4j
public class NewbieBrandController {
    @Autowired
    public INewbieBrandService newbieBrandService;

    /**
     * Get All Brands By Page
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Operation(summary = "获取品牌列表-分页")
    @GetMapping(value = "/getAllByPage")
    public Result<IPage<NewbieBrand>> getAllBrandsByPage(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {

        Result<IPage<NewbieBrand>> result = new Result<>();
        IPage<NewbieBrand> pageList = null;
        try {
            LambdaQueryWrapper<NewbieBrand> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(NewbieBrand::getIsDeleted, 0);
            lambdaQueryWrapper.orderByAsc(NewbieBrand::getName);
            pageList = newbieBrandService.page(
                    new Page<>(pageNo, pageSize), lambdaQueryWrapper);
            result.setSuccess(true);
            result.setResult(pageList);
            return result;
        } catch (Exception e) {
            log.error("Error fetching brands by page", e);
            result.setSuccess(false);
            result.setMessage("Error fetching brands: " + e.getMessage());
            return result;
        }
    }

    @Operation(summary = "获取全部品牌列表")
    @GetMapping(value = "/getAll")
    public Result<List<NewbieBrand>> getAllBrands() {

        Result<List<NewbieBrand>> result = new Result<>();
        try {
            LambdaQueryWrapper<NewbieBrand> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(NewbieBrand::getIsDeleted, 0);
            lambdaQueryWrapper.orderByAsc(NewbieBrand::getName);
            List<NewbieBrand> list = newbieBrandService.list(lambdaQueryWrapper);
            result.setSuccess(true);
            result.setResult(list);
            return result;
        } catch (Exception e) {
            log.error("Error fetching brands by page", e);
            result.setSuccess(false);
            result.setMessage("Error fetching brands: " + e.getMessage());
            return result;
        }
    }

    @Operation(summary = "获取品牌By ID")
    @GetMapping(value = "/getById/{id}")
    public Result<NewbieBrand> getById(@PathVariable String id) {
        Result<NewbieBrand> result = new Result<>();
        NewbieBrand brand = newbieBrandService.getById(id);
        if (brand == null || brand.getIsDeleted() == 1) {
            result = result.error500("未找到对应实体");
        } else {
            result.setResult(brand);
            result.success("查询成功");
        }

        return result;
    }

    @Operation(summary = "通过id更新品牌信息")
    @PutMapping(value = "/updateById")
    public Result<NewbieBrand> updateById(@RequestBody NewbieBrand newbieBrand) {
        Result<NewbieBrand> result = new Result<>();
        NewbieBrand oldBrand = newbieBrandService.getById(newbieBrand.getId());
        if (oldBrand == null) {
            result = result.error500("未找到对应实体");
        } else {
            boolean isOk = newbieBrandService.updateById(newbieBrand);
            if (isOk) {
                result.success("更新成功");
            }
        }

        return result;
    }

    @Operation(summary = "通过id删除品牌信息")
    @DeleteMapping(value = "/deleteById/{id}")
    public Result<String> deleteById(@PathVariable String id) {
        try {
            NewbieBrand brandToUpdate = new NewbieBrand();
            brandToUpdate.setId(id);
            brandToUpdate.setIsDeleted(1);
            brandToUpdate.setUpdateTime(new Date());
            newbieBrandService.updateById(brandToUpdate);
        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.error("删除失败!");
        }

        return Result.ok("删除成功!");
    }

    @Operation(summary = "添加品牌")
    @PostMapping(value = "/addBrand")
    public Result<String> addBrand(@RequestBody NewbieBrand newbieBrand) {
        Result<String> result = new Result<>();
        try {
            newbieBrandService.save(newbieBrand);
            result.success("添加成功");
        } catch (Exception e) {
            log.error("添加失败", e);
            result.error500("操作失败");
        }
        return result;
    }

}
