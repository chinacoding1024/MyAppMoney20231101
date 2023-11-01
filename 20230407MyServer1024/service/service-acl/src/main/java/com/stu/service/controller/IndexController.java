package com.stu.service.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.stu.base.result.R;
import com.stu.security.utils.Const;
import com.stu.security.utils.RedisUtil;
import com.stu.service.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/******************************
 * 用途说明:
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
@RestController
@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    Producer producer;
    @Autowired
    RedisUtil redisUtil;
    @GetMapping("/captcha")
    public R captcha() throws IOException {

        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        // 为了测试
//        key = "aaaaa";
//        code = "123456";

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());


        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);

        return R.ok().data("data",MapUtil.builder()
                .put("token", key)
                .put("captchaImg", base64Img)
                .build());
    }


    /***********************************
     * 用途说明:退出
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @PostMapping("logout")
    public R logout() {
        return R.ok();
    }

    /***********************************
     * 用途说明:
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @GetMapping("getUserInfo")
    public R getUserInfo() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(userName);

        List<JSONObject> permissionList = indexService.getMenu(userName);

        return R.ok().data(userInfo).data("permissionList", permissionList);
    }

    /***********************************
     * 用途说明:根据用户明获取动态菜单
     * 返回值说明:
     * @return com.stu.service.base.result.R
     ***********************************/
    @GetMapping("menu")
    public R menu() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(userName);
        return R.ok().data("permissionList", permissionList);
    }
}
