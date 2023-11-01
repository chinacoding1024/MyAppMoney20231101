package com.stu.security.Handler;

import com.stu.base.result.R;
import com.stu.security.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/******************************
 * 用途说明: 登录失败处理器处理
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        /*response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        R r = R.error().message(exception.getMessage());
        outputStream.write(JSONUtil.toJsonStr(r).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();*/
        ResponseUtil.out(response, R.error().message(exception.getMessage()));
    }
}
