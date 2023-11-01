package com.stu.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.base.result.R;
import com.stu.base.result.ResultCodeEnum;
import com.stu.service.entity.UserRole;
import com.stu.service.service.IUserRoleService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/******************************
 * 用途说明:
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@RestController
@CrossOrigin
@RequestMapping("/admin/sys/userRole")
public class UserRoleController {

    @Resource
    private IUserRoleService userRoleService;

    /***********************************
     * 用途说明:新增或者更新
     * @param userRole
     * 返回值说明:
     * @return R
     ***********************************/
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody UserRole userRole) {
        boolean result = userRoleService.saveOrUpdate(userRole);
        if (result) {
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        } else {
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }


    /***********************************
     * 用途说明:新增
     * @param userRole
     * 返回值说明:
     * @return R
     ***********************************/
    @PostMapping("add")
    public R add(@RequestBody UserRole userRole) {
        boolean result = userRoleService.save(userRole);
        if (result) {
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        } else {
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }

    /***********************************
     * 用途说明:删除
     * @param id
     * 返回值说明:
     * @return R
     ***********************************/
    @DeleteMapping("/del/{id}")
    public R delete(@PathVariable String id) {
        boolean result = userRoleService.removeById(id);
        if (result) {
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        } else {
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }

    /***********************************
     * 用途说明:批量删除
     * @param ids
     * 返回值说明:
     * @return R
     ***********************************/
    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        boolean result = userRoleService.removeByIds(ids);
        if (result) {
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        } else {
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }

    /***********************************
     * 用途说明:取得所有对象
     * @param:
     * 返回值说明:
     * @return R
     ***********************************/
    @GetMapping
    public R findAll() {
        return R.ok().data("data", userRoleService.list());
    }

    /***********************************
     * 用途说明:根据id取得对象
     * @param id
     * 返回值说明:
     * @return R
     ***********************************/
    @GetMapping("/{id}")
    public R findOne(@PathVariable String id) {
        return R.ok().data("data", userRoleService.getById(id));
    }

    /***********************************
     * 用途说明:分页
     * @param pageNum pageSize userRole
     * 返回值说明:
     * @return R
     ***********************************/
    @PostMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestBody UserRole userRole) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return R.ok().data("data", userRoleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

