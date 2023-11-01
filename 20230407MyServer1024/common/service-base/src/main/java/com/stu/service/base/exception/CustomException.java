    package com.stu.service.base.exception;

    import com.stu.base.result.ResultCodeEnum;
    import lombok.Data;

    /******************************
     * 用途说明:  自定义异常
     * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
     * 创建时间: 2022-07-27 23:16
     ******************************/
    @Data
    public class CustomException extends RuntimeException{

        private Integer code;

        public CustomException(ResultCodeEnum resultCodeEnum){
            super(resultCodeEnum.getMessage());
            //this.message = resultCodeEnum.getMessage();
            this.code = resultCodeEnum.getCode();

        }

    }
