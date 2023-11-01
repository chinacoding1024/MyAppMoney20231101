package com.stu.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.service.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author 程序员小明1024
 * @since 2023-08-07
 */
public interface IPermissionService extends IService<Permission> {

    /***********************************
     * 用途说明:获取全部菜单
     * 返回值说明:
     * @return com.stu.myserver.utils.R
     ***********************************/
    List<Permission> listPermissions();
    /***********************************
     * 用途说明:递归删除菜单
     * @param id
     * 返回值说明:
     * @return boolean
     ***********************************/
    boolean removeChildById(String id);

    /***********************************
     * 用途说明:根據角色獲取菜單
     * @param id
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Permission>
     ***********************************/
    List<Permission> listAllMenu(String id);

    /***********************************
     * 用途说明:给角色分配菜单权限
     * @param roleId
     * @param permissionId
     * 返回值说明:
     * @return boolean
     ***********************************/
    boolean saveRolePermissionrelationShip(String roleId, String[] permissionId);

    /***********************************
     * 用途说明:根据用户id查询有权限的菜单
     * @param id
     * 返回值说明:
     * @return java.util.List<java.lang.String>
     ***********************************/
    List<String> selectPermissionValueListByUserId(String id);

    /***********************************
     * 用途说明:根据用户id查询所有权限的菜单详细列表
     * @param userId
     * 返回值说明:
     * @return java.util.List<org.json.JSONObject>
     ***********************************/
    List<JSONObject> selectPermissionByUserId(String userId);

    /***********************************
     * 用途说明:查询所有权限菜单
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Permission>
     ***********************************/
    List<Permission> ListAllPermissions();
}
