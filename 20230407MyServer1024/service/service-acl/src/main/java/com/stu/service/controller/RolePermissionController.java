package com.stu.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.base.result.R;
import com.stu.base.result.ResultCodeEnum;
import com.stu.service.entity.RolePermission;
import com.stu.service.service.IRolePermissionService;

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
@RequestMapping("/admin/sys/permission")
public class RolePermissionController {

    @Resource
    private IRolePermissionService rolePermissionService;

    // 新增或者更新
    /***********************************
     * 用途说明:
     * @param rolePermission
     * 返回值说明:
     * @return R
     ***********************************/
    @PostMapping("add")
    public R add(@RequestBody RolePermission rolePermission){
        boolean result= rolePermissionService.save(rolePermission);
        if(result){
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        }else{
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }

    /***********************************
     * 用途说明:
     * @param id
     * 返回值说明:
     * @return R
     ***********************************/
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id){
        boolean result= rolePermissionService.removeById(id);
        if(result){
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        }else{
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }

    /***********************************
     * 用途说明:
     * @param ids
     * 返回值说明:
     * @return R
     ***********************************/
    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids){
        boolean result=  rolePermissionService.removeByIds(ids);
        if(result){
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        }else{
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }

    /***********************************
     * 用途说明:
     * @param:
     * 返回值说明:
     * @return R
     ***********************************/
    @GetMapping
    public R findAll(){
        return  R.ok().data("data", rolePermissionService.list());
    }

    /***********************************
     * 用途说明:
     * @param id
     * 返回值说明:
     * @return R
     ***********************************/
    @GetMapping("/{id}")
    public R findOne(@PathVariable Integer id){
        return R.ok().data("data", rolePermissionService.getById(id));
    }

    /***********************************
     * 用途说明:
     * @param pageNum pageSize rolePermission
     * 返回值说明:
     * @return  R
     ***********************************/
    @PostMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestBody RolePermission rolePermission){
        QueryWrapper<RolePermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return R.ok().data("data", rolePermissionService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }
}

