package com.stu.security.Handler;


import cn.hutool.json.JSONUtil;
import com.stu.base.result.R;
import com.stu.security.utils.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/******************************
 * 用途说明: 权限不足操作类，全局异常之后捕获
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

/*        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ServletOutputStream outputStream = response.getOutputStream();

        R result = R.error().message(accessDeniedException.getMessage());

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();*/

        ResponseUtil.out(response, R.error().message(accessDeniedException.getMessage()));

    }
}
