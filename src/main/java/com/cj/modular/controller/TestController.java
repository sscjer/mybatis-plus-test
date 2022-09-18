package com.cj.modular.controller;

import com.cj.modular.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试接口的类
 * </p>
 *
 * @author Caoj
 * @version 2022-08-16 18:58
 */
@RestController("/test/")
public class TestController {
    @Autowired
    private TUserService tUserService;

    @RequestMapping("doTest")
    public Object testMethod() {
        return tUserService.getBaseMapper().selectList(null);
    }

}
