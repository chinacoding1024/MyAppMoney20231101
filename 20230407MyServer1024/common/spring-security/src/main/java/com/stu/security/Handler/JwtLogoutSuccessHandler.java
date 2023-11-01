package com.stu.security.Handler;

import cn.hutool.json.JSONUtil;
import com.stu.security.utils.JwtUtils;
import com.stu.base.result.R;
import com.stu.security.utils.ResponseUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/******************************
 * 用途说明: 登出成功处理
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;
    private JwtUtils tokenManager;
    private RedisTemplate redisTemplate;

    public JwtLogoutSuccessHandler(JwtUtils tokenManager, RedisTemplate redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;

    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        String token = request.getHeader(jwtUtils.getHeader());
        Claims claim = jwtUtils.getClaimByToken(token);
        if (claim != null && !jwtUtils.isTokenExpired(claim)) {
            String userName = claim.getSubject();
            redisTemplate.delete(userName);

        }
        response.setHeader(jwtUtils.getHeader(), "");

        /*response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();



        R result = R.ok();

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();*/

        ResponseUtil.out(response,R.ok() );
    }
}
