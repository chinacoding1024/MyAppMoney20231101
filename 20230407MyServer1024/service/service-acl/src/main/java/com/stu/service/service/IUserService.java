package com.stu.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stu.service.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 程序员小明1024
 * @since 2023-06-23
 */
public interface IUserService extends IService<User> {
    /***********************************
     * 用途说明:获取用户详情
     * @param username
     * 返回值说明:
     * @return com.stu.service.acl.entity.User
     ***********************************/
    User selectByUserName(String username);
}
