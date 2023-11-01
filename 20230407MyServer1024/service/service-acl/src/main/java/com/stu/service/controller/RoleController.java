package com.stu.service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.base.result.R;
import com.stu.service.entity.Role;
import com.stu.service.service.IRoleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************
 * 用途说明:
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@RestController
@CrossOrigin
@RequestMapping("/admin/sys/role")
public class RoleController {

    @Resource
    private IRoleService roleService;
    /***********************************
     * 用途说明:删除角色
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @DeleteMapping("deleteRole/{id}")
    public R deleteRole(@PathVariable String id) {

        /*        if (true) {
            throw new CustomException(ResultCodeEnum.DIVIDE_ZERO);
        }*/

        boolean result = roleService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }

    /***********************************
     * 用途说明:修改角色
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @PostMapping("updateRole")
    public R updateRole(@RequestBody Role role) {
        boolean result = roleService.updateById(role);
        if (result) {
            return R.ok().message("更新成功");
        }
        return R.error().message("更新失败");
    }

    /***********************************
     * 用途说明:添加角色
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @ApiOperation("添加角色")
    @PostMapping("addRole")
    public R addRole(@ApiParam("角色对象") @RequestBody Role role) {
        boolean result = roleService.saveOrUpdate(role);
        if (result) {
            return R.ok().message("保存成功");
        }
        return R.error().message("保存失败");
    }

    /***********************************
     * 用途说明:查询角色表所有数据
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @GetMapping("findAll")
    public R findAllRole() {
        List<Role> list = roleService.list();
        return R.ok().data("list", list);
    }

    /***********************************
     * 用途说明:查询
     * 返回值说明: com.stu.service.edu.entity.Role
     ***********************************/
    @GetMapping("get/{id}")
    public R getRole(@PathVariable String id) {

        return R.ok().data("dataInfo", roleService.getById(id));

    }

    /***********************************
     * 用途说明:查询角色表所有数据
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @GetMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable long page, @PathVariable long limit, Role role) {
        Page<Role> pageParam = new Page<>(page, limit);

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(role.getRoleName())) {
            queryWrapper.like("role_name", role.getRoleName());
        }
        roleService.page(pageParam, queryWrapper);
        Map<String, Object> map = new HashMap<String, Object>();
        long total = pageParam.getTotal();
        List<Role> list = pageParam.getRecords();
        map.put("total", total);
        map.put("list", list);
        return R.ok().data(map);

    }
//    // 新增或者更新
//    /***********************************
//     * 用途说明:
//     * @param role
//     * 返回值说明:
//     * @return R
//     ***********************************/
//    @PostMapping("add")
//    public R add(@RequestBody Role role){
//        boolean result= roleService.save(role);
//        if(result){
//            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
//        }else{
//            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
//        }
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param id
//     * 返回值说明:
//     * @return R
//     ***********************************/
//    @DeleteMapping("/{id}")
//    public R delete(@PathVariable Integer id){
//        boolean result= roleService.removeById(id);
//        if(result){
//            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
//        }else{
//            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
//        }
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param ids
//     * 返回值说明:
//     * @return R
//     ***********************************/
//    @PostMapping("/del/batch")
//    public R deleteBatch(@RequestBody List<Integer> ids){
//        boolean result=  roleService.removeByIds(ids);
//        if(result){
//            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
//        }else{
//            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
//        }
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param:
//     * 返回值说明:
//     * @return R
//     ***********************************/
//    @GetMapping
//    public R findAll(){
//        return  R.ok().data("data", roleService.list());
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param id
//     * 返回值说明:
//     * @return R
//     ***********************************/
//    @GetMapping("/{id}")
//    public R findOne(@PathVariable Integer id){
//        return R.ok().data("data", roleService.getById(id));
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param pageNum pageSize role
//     * 返回值说明:
//     * @return  R
//     ***********************************/
//    @PostMapping("/page")
//    public R findPage(@RequestParam Integer pageNum,
//                                    @RequestParam Integer pageSize,
//                                    @RequestBody Role role){
//        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
//        return R.ok().data("data", roleService.page(new Page<>(pageNum,pageSize),queryWrapper));
//    }
}

