package com.stu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.service.entity.User;
import com.stu.service.mapper.UserMapper;
import com.stu.service.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 程序员小明1024
 * @since 2023-06-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /***********************************
     * 用途说明:获取用户详情
     * @param username
     * 返回值说明:
     * @return username
     ***********************************/
    @Override
    public User selectByUserName(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));

    }
}
