package com.stu.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.service.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 程序员小明1024
 * @since 2023-06-28
 */
public interface IRoleService extends IService<Role> {
    /***********************************
     * 用途说明:根据用户获取角色
     * @param userId
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Permission>
     ***********************************/
    Map<String, Object> findRoleByUserId(String userId);

    /***********************************
     * 用途说明:给用户分配角色权限
     * @param userId
     * @param permissionIds
     * 返回值说明:
     * @return boolean
     ***********************************/
    boolean saveUserRelationShip(String userId, String[] permissionIds);

    /***********************************
     * 用途说明:根据userid获取用户信息
     * @param id
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Role>
     ***********************************/
    List<Role> selectRoleByUserId(String id);
}
