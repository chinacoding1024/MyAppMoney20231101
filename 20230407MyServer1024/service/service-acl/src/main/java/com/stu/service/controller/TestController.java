package com.stu.service.controller;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import com.stu.base.result.*;
import org.springframework.web.bind.annotation.PathVariable;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.service.service.ITestService;
import com.stu.service.entity.Test;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 
 * </p>
 *
 * @author 公众号 小明的学习圈子
 * @since 2023-10-27
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ITestService testService;

    /***********************************
     * 用途说明:新增或者更新
     * 更多内容:公众号 小明的学习圈子 https://www.stucoding.com/
     * @param test
     * 返回值说明:
     * @return R
     ***********************************/
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Test test){
        boolean result= testService.saveOrUpdate(test);
        if(result){
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        }else{
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }


    /***********************************
     * 用途说明:新增
     * @param test
     * 返回值说明:
     * @return R
     ***********************************/
    @PostMapping("add")
    public R add(@RequestBody Test test){
        boolean result= testService.save(test);
        if(result){
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        }else{
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }

    /***********************************
     * 用途说明:删除
     * @param id
     * 返回值说明:
     * @return R
     ***********************************/
    @DeleteMapping("/del/{id}")
    public R delete(@PathVariable String id){
        boolean result= testService.removeById(id);
        if(result){
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        }else{
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }

    /***********************************
     * 用途说明:批量删除
     * @param ids
     * 返回值说明:
     * @return R
     ***********************************/
    @PostMapping("/del/batch")
    public R deleteBatch(@RequestBody List<String> ids){
        boolean result=  testService.removeByIds(ids);
        if(result){
            return R.ok().message(ResultCodeEnum.SUCCESS.getMessage());
        }else{
            return R.error().message(ResultCodeEnum.UPDATE_ERROR.getMessage());
        }
    }

    /***********************************
     * 用途说明:取得所有对象
     * @param:
     * 返回值说明:
     * @return R
     ***********************************/
    @GetMapping
    public R findAll(){
        return  R.ok().data("data", testService.list());
    }

    /***********************************
     * 用途说明:根据id取得对象
     * @param id
     * 返回值说明:
     * @return R
     ***********************************/
    @GetMapping("/{id}")
    public R findOne(@PathVariable String id){
        return R.ok().data("data", testService.getById(id));
    }

    /***********************************
     * 用途说明:分页
     * @param pageNum pageSize test
     * 返回值说明:
     * @return  R
     ***********************************/
    @PostMapping("/page")
    public R findPage(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestBody Test test){
        QueryWrapper<Test> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return R.ok().data("data", testService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }
}

