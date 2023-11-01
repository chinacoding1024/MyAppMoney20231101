package com.stu.service.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

/******************************
 * 用途说明:
 * 作者姓名:公众号:程序员小明1024
 * 创建时间: 2023-06-23 14:52
 ******************************/
public class CodeGenerator {

    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        String projectPath = System.getProperty("user.dir")+"/service/service-acl";

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/myServer?serverTimezone=Asia/Shanghai", "root", "study")
                .globalConfig(builder -> {
                    builder.author("公众号 小明的学习圈子") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.stu.service") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok()
                            .addTableFills(new Column("gmt_create", FieldFill.INSERT))
                            .addTableFills(new Column("gmt_modified", FieldFill.INSERT_UPDATE));;
//                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("test") // 设置需要生成的表名
                            .addTablePrefix("acl_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }


}
