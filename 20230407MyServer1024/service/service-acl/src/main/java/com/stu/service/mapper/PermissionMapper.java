package com.stu.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.service.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author 程序员小明1024
 * @since 2023-08-07
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /***********************************
     * 用途说明:获取全部菜单的权限
     * 返回值说明:
     * @return java.util.List<java.lang.String>
     ***********************************/
    List<String> selectAllPermissionValue();

    /***********************************
     * 用途说明:根据用户id查询所有权限
     * @param id
     * 返回值说明:
     * @return java.util.List<java.lang.String>
     ***********************************/
    List<String> selectPermissionValueByUserId(String id);

    /***********************************
     * 用途说明:根据用户id查询所有菜单
     * @param userId
     * 返回值说明:
     * @return java.util.List<com.stu.service.acl.entity.Permission>
     ***********************************/
    List<Permission> selectPermissionByUserId(String userId);
}
