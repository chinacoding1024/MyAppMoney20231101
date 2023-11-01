package com.stu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.service.entity.Role;
import com.stu.service.entity.UserRole;
import com.stu.service.mapper.RoleMapper;
import com.stu.service.service.IRoleService;
import com.stu.service.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 程序员小明1024
 * @since 2023-06-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private IUserRoleService userRoleService;

    /***********************************
     * 用途说明:根据用户获取角色
     * @param userId
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Permission>
     ***********************************/
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        //获取所有角色
        List<Role> allRoleList = baseMapper.selectList(new QueryWrapper<>());
        //根据用户id获取角色列表
        List<UserRole> existUserRoleList = userRoleService
                .list(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id"));
        //遍历所有菜单，获取每一项，看是否在权限列表，如果在，就标记
        List<String> existRoleLists = existUserRoleList.stream().map(e -> e.getRoleId()).collect(Collectors.toList());

        List<Role> assignRoles = new ArrayList<>();
        allRoleList.forEach(role -> {
            if (existRoleLists.contains(role.getId())) {
                assignRoles.add(role);
            }
        });

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRoleList", allRoleList);
        return roleMap;
    }

    /***********************************
     * 用途说明:给用户分配角色权限
     * @param userId
     * @param roleIds
     * 返回值说明:
     * @return boolean
     ***********************************/
    @Override
    public boolean saveUserRelationShip(String userId, String[] roleIds) {
        //删除旧的所有角色权限
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        List<UserRole> list = new ArrayList<>();
        for (String id : roleIds) {
            UserRole rolePermission = new UserRole();
            rolePermission.setRoleId(id);
            rolePermission.setUserId(userId);
            list.add(rolePermission);
        }

        return userRoleService.saveBatch(list);
    }

    /***********************************
     * 用途说明:根据userid获取用户信息
     * @param userId
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Role>
     ***********************************/
    @Override
    public List<Role> selectRoleByUserId(String userId) {
        //根据用户id获取角色列表
        List<UserRole> userRoleList = userRoleService
                .list(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id"));
        //遍历所有菜单，获取每一项，看是否在权限列表，如果在，就标记
        List<String> roleIdLists = userRoleList.stream().map(e -> e.getRoleId()).collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        if (roleIdLists.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdLists);
        }

        return roleList;
    }
}
