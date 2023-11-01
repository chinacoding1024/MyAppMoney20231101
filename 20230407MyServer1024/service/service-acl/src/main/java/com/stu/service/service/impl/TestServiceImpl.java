package com.stu.service.service.impl;

import com.stu.service.entity.Test;
import com.stu.service.mapper.TestMapper;
import com.stu.service.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 公众号 小明的学习圈子
 * @since 2023-10-27
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
