package com.stu.security.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/******************************
 * 用途说明: Security错误信息中英文转换
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@Configuration
public class MySecurityMessages {

    /**
     * 自定义错误信息
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.CHINA);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        //中文提示信息配置文件
        messageSource.addBasenames("classpath:messages_zh_CN");
        return messageSource;
    }
}
