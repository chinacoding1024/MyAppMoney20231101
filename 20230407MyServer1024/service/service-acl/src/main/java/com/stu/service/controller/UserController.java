package com.stu.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.base.result.R;
import com.stu.base.utils.MD5;
import com.stu.service.entity.User;
import com.stu.service.service.IRoleService;
import com.stu.service.service.IUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************
 * 用途说明:
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@RestController
@RequestMapping("/admin/sys/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    /***********************************
     * 用途说明:删除角色
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @DeleteMapping("deleteUser/{id}")
    public R deleteUser(@PathVariable String id) {

        /*        if (true) {
            throw new CustomException(ResultCodeEnum.DIVIDE_ZERO);
        }*/

        boolean result = userService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }

    /***********************************
     * 用途说明:修改角色
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @PostMapping("updateUser")
    public R updateUser(@RequestBody User user) {
        boolean result = userService.updateById(user);
        if (result) {
            return R.ok().message("更新成功");
        }
        return R.error().message("更新失败");
    }

    /***********************************
     * 用途说明:添加角色
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
//    @PreAuthorize("hasAuthority('sys:user:lista')")
    @ApiOperation("添加角色")
    @PostMapping("addUser")
    public R addUser(@ApiParam("角色对象") @RequestBody User user) {

        user.setPassword(MD5.encrypt("111111"));
        boolean result = userService.save(user);
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
    public R findAllUser() {
        List<User> list = userService.list();
        return R.ok().data("list", list);
    }

    /***********************************
     * 用途说明:查询
     * 返回值说明: com.stu.service.edu.entity.User
     ***********************************/
    @GetMapping("get/{id}")
    public R getUser(@PathVariable String id) {

        return R.ok().data("dataInfo", userService.getById(id));

    }

    /***********************************
     * 用途说明:查询角色表所有数据
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
//    @PreAuthorize("hasRole('admin')")
    @GetMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable long page, @PathVariable long limit, User user) {
        Page<User> pageParam = new Page<>(page, limit);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(user.getUsername())) {
            queryWrapper.like("user_name", user.getUsername());
        }
        userService.page(pageParam, queryWrapper);
        Map<String, Object> map = new HashMap<String, Object>();
        long total = pageParam.getTotal();
        List<User> list = pageParam.getRecords();
        map.put("total", total);
        map.put("list", list);
        return R.ok().data(map);

    }

    /***********************************
     * 用途说明:
     * @param pageNum pageSize user
     * 返回值说明:
     * @return Page<User>
     ***********************************/
    //@PreAuthorize("hasRole('test')")
    @PostMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return userService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    /***********************************
     * 用途说明:根据用户获取角色
     * @param userId
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @GetMapping("toAssign/{userId}")
    public R toAssign(@PathVariable String userId) {
        return R.ok().data("data", roleService.findRoleByUserId(userId));
    }


    /***********************************
     * 用途说明:给用户分配角色权限
     * @param userId
     * @param permissionIds
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @PostMapping("doAssign")
    public R doAssign(String userId, String[] permissionIds) {
        boolean result = roleService.saveUserRelationShip(userId, permissionIds);
        if (result) {
            return R.ok();
        }
        return R.error();

    }

//    // 新增或者更新
//    /***********************************
//     * 用途说明:
//     * @param user
//     * 返回值说明:
//     * @return boolean
//     ***********************************/
//    @PostMapping("/save")
//    public boolean save(@RequestBody User user){
//        return userService.saveOrUpdate(user);
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param id
//     * 返回值说明:
//     * @return Boolean
//     ***********************************/
//    @DeleteMapping("/{id}")
//    public Boolean delete(@PathVariable Integer id){
//        return userService.removeById(id);
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param ids
//     * 返回值说明:
//     * @return boolean
//     ***********************************/
//    @PostMapping("/del/batch")
//    public boolean deleteBatch(@RequestBody List<Integer> ids){
//        return userService.removeByIds(ids);
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param:
//     * 返回值说明:
//     * @return List<User>
//     ***********************************/
//    @GetMapping
//    public List<User> findAll(){
//        return userService.list();
//    }
//
//    /***********************************
//     * 用途说明:
//     * @param id
//     * 返回值说明:
//     * @return User
//     ***********************************/
//    @GetMapping("/{id}")
//    public User findOne(@PathVariable Integer id){
//        return userService.getById(id);
//    }
//


}

