package com.stu.service.controller;

import com.stu.base.result.R;
import com.stu.service.entity.Permission;
import com.stu.service.service.IPermissionService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/******************************
 * 用途说明:
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@RestController
@CrossOrigin
@RequestMapping("/admin/sys/permission")
public class PermissionController {

    @Resource
    private IPermissionService permissionService;
    /***********************************
     * 用途说明:查询所有权限菜单
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @GetMapping("ListAllPermissions")
    public R ListAllPermissions() {
        return R.ok().data("data", permissionService.ListAllPermissions());
    }

    /***********************************
     * 用途说明:新增
     * @param permission
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @PostMapping("save")
    public R saveMenu(@RequestBody Permission permission) {
        boolean result = permissionService.save(permission);
        if (result) {
            return R.ok();
        }
        return R.error();
    }

    /***********************************
     * 用途说明:修改
     * @param permission
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @PutMapping("update")
    public R update(@RequestBody Permission permission) {
        boolean result = permissionService.updateById(permission);
        if (result) {
            return R.ok();
        }
        return R.error();
    }

    /***********************************
     * 用途说明:递归删除菜单
     * @param id
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id) {
        boolean result = permissionService.removeChildById(id);
        if (result) {
            return R.ok();
        }
        return R.error();
    }


    /***********************************
     * 用途说明:根据角色获取菜单
     * @param roleId
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        return R.ok().data("data", permissionService.listAllMenu(roleId));
    }


    /***********************************
     * 用途说明:给角色分配菜单权限
     * @param roleId
     * @param permissionId
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @PostMapping("doAssign")
    public R doAssign(String roleId, String[] permissionId) {
        boolean result = permissionService.saveRolePermissionrelationShip(roleId, permissionId);
        if (result) {
            return R.ok();
        }
        return R.error();

    }
    /***********************************
     * 用途说明:获取全部菜单
     * 返回值说明:
     * @return com.stu.myserver.utils.R
     ***********************************/
    @GetMapping("/getAllMenuList")
    public R getAllMenuList() {
        return R.ok().data("data", permissionService.listPermissions());
    }
//
//
//    // 新增或者更新
//
//    /***********************************
//     * 用途说明:
//     * @param permission
//     * 返回值说明:
//     * @return R
//     ***********************************/
//    @PostMapping("/add")
//    public R add(@RequestBody Permission permission) {
//        boolean result = permissionService.save(permission);
//        if (result) {
//            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
//        } else {
//            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
//        }
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param permission
//     * 返回值说明:
//     * @return R
//     ***********************************/
//    @PostMapping("update")
//    public R update(@RequestBody Permission permission) {
//        boolean result = permissionService.updateById(permission);
//        if (result) {
//            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
//        } else {
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
//    @DeleteMapping("/delete/{id}")
//    public R delete(@PathVariable String id) {
//        boolean result = permissionService.removeById(id);
//        if (result) {
//            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
//        } else {
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
//    public R deleteBatch(@RequestBody List<Integer> ids) {
//        boolean result = permissionService.removeByIds(ids);
//        if (result) {
//            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
//        } else {
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
//    public R findAll() {
//        return R.ok().data("data", permissionService.list());
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param id
//     * 返回值说明:
//     * @return R
//     ***********************************/
//    @GetMapping("/{id}")
//    public R findOne(@PathVariable Integer id) {
//        return R.ok().data("data", permissionService.getById(id));
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param pageNum pageSize permission
//     * 返回值说明:
//     * @return R
//     ***********************************/
//    @PostMapping("/page")
//    public R findPage(@RequestParam Integer pageNum,
//                      @RequestParam Integer pageSize,
//                      @RequestBody Permission permission) {
//        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
//        return R.ok().data("data", permissionService.page(new Page<>(pageNum, pageSize), queryWrapper));
//    }
}

