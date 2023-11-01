package com.stu.service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface IndexService {

    /***********************************
     * 用途说明:根据用户明获取用户登录信息
     * @param userName
     * 返回值说明:
     * @return java.util.Map<java.lang.String, java.lang.Object>
     ***********************************/
    Map<String, Object> getUserInfo(String userName);

    /***********************************
     * 用途说明:根据用户动态获取菜单
     * @param userName
     * 返回值说明:
     * @return java.util.List<org.json.JSONObject>
     ***********************************/
    List<JSONObject> getMenu(String userName);
}
