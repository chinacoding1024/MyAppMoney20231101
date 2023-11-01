package com.stu.base.utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
/******************************
 * 用途说明:更多内容 公众号 小明的学习圈子 https://www.stucoding.com/
 * 作者姓名: Administrator
 * 创建时间: 2022-04-18 22:26
 ******************************/

public class ExceptionUtils {

    /***********************************
     * 用途说明:
     * 返回值说明: java.lang.String
     ***********************************/
    public static String getMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();

    }
}