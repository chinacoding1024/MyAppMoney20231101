package com.stu.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/******************************
 * 用途说明:表单验证，字符串操作工具类
 * 作者姓名: 更多内容 公众号 小明的学习圈子 https://www.stucoding.com/
 * 创建时间: 2022-06-30 22:25
 ******************************/
public class FormUtils {

    public static boolean checkMobile(String str){
       Pattern pattern = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$");
       Matcher matcher = pattern.matcher(str);
       return matcher.matches();
    }
}
