package com.stu.service.service.impl;

import com.stu.base.result.ResultCodeEnum;
import com.stu.security.entity.User;
import com.stu.service.service.IPermissionService;
import com.stu.service.service.IUserService;
import com.stu.security.entity.SecurityUser;
import com.stu.service.base.exception.CustomException;
import com.stu.service.service.IndexService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/******************************
 * 用途说明: 自定义UserDetailsService实现类，认证用户详情
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IndexService indexService;

    /**
     * 根据用户名查询用户信息
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.stu.service.entity.User user = userService.selectByUserName(username);
        if (user == null) {
            throw new CustomException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }
        // 返回UserDetails实现类
        com.stu.security.entity.User curUser = new User();
        BeanUtils.copyProperties(user, curUser);
        //根据用户id查询有权限的菜单
        List<String> authorities = permissionService.selectPermissionValueListByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);

        indexService.getUserInfo(username);
        return securityUser;
    }

}
