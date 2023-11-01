package com.stu.base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/******************************
 * 用途说明:更多内容 公众号 小明的学习圈子 https://www.stucoding.com/
 * 作者姓名: Administrator
 * 创建时间: 2022-06-30 22:38
 ******************************/
public final class MD5 {

    public static String encrypt(String strSrc) {
        try {
            char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5.encrypt("111111"));

        System.out.println(MD5.encrypt("admin"));

        System.out.println(MD5.encrypt("96e79218965eb72c92a549dd5a330112"));
    }

}
