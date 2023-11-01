package com.stu.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.stu.base.result.ResultCodeEnum;
import com.stu.service.base.exception.CustomException;
import com.stu.service.entity.Role;
import com.stu.service.entity.User;

import com.stu.service.service.IPermissionService;
import com.stu.service.service.IRoleService;
import com.stu.service.service.IUserService;
import com.stu.service.service.IndexService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/******************************
 * 用途说明:
 * 作者姓名: Administrator
 * 创建时间: 2022-09-01 22:34
 ******************************/
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /***********************************
     * 用途说明:根据用户明获取用户登录信息
     * @param userName
     * 返回值说明:
     * @return java.util.Map<java.lang.String, java.lang.Object>
     ***********************************/
    @Override
    public Map<String, Object> getUserInfo(String userName) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.selectByUserName(userName);
        if (user == null) {
            throw new CustomException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        //转换成角色名称列表
        List<String> roleNameList = roleList.stream()
                .map(item -> item.getRoleName()).collect(Collectors.toList());

        //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
        if (roleNameList.size() == 0) {
            roleNameList.add("");
        }

        List<String> permissionValueList = permissionService.selectPermissionValueListByUserId(user.getId());
        redisTemplate.opsForValue().set(userName, permissionValueList);
        List<String> permissionValueLisst = (List<String>) redisTemplate.opsForValue().get(userName);
        result.put("name", user.getUsername());
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /***********************************
     * 用途说明:根据用户动态获取菜单
     * @param userName
     * 返回值说明:
     * @return java.util.List<org.json.JSONObject>
     ***********************************/
    @Override
    public List<JSONObject> getMenu(String userName) {
        User user = userService.selectByUserName(userName);
        if (user == null) {
            throw new CustomException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
        //根据用户动态获取菜单
        return permissionService.selectPermissionByUserId(user.getId());
    }
}
