package com.stu.base.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/******************************
 * 用途说明:更多内容 公众号 小明的学习圈子 https://www.stucoding.com/
 * 作者姓名: Administrator
 * 创建时间: 2022-06-30 21:47
 ******************************/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo {
    private String id;
    private String nickName;
    private String avatar;
    //权限、角色等
    //不要存敏感信息
}
