package com.stu.service.base.handler;

import com.stu.base.utils.ExceptionUtils;
import com.stu.base.result.R;
import com.stu.service.base.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/******************************
 * 用途说明:统一异常处理
 * 作者姓名: 更多内容 公众号 小明的学习圈子 https://www.stucoding.com/
 * 创建时间: 2022-04-17 23:32
 ******************************/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /***********************************
     * 用途说明:自定义异常
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public R customException(CustomException e){
//        e.printStackTrace();
        log.error(e.getMessage());
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }
    /***********************************
     * 用途说明:数字异常
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R arithmeticException(Exception e){
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage());
    }
    /***********************************
     * 用途说明:全局异常
     * 返回值说明: com.stu.service.base.result.R
     ***********************************/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage());
    }

}
