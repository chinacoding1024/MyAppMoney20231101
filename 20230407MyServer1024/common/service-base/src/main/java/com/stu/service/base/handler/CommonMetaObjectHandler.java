package com.stu.service.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

/******************************
 * 用途说明:自动填充
 * 作者姓名: 更多内容 公众号 小明的学习圈子 https://www.stucoding.com/
 * 创建时间: 2022-04-17 21:18
 ******************************/
@EnableTransactionManagement
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "gmtCreate", () -> new Date(), Date.class); // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "gmtModified", () -> new Date(), Date.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "gmtModified", () -> new Date(), Date.class); // 起始版本 3.3.3(推荐)
    }

}