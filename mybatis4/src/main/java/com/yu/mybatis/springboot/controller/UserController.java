package com.yu.mybatis.springboot.controller;

import com.yu.mybatis.simple.model.SysUser;
import com.yu.mybatis.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created By Yu On 2018/8/16
 * Description：
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("users/{id}")
    SysUser user(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @RequestMapping("users")
    List<SysUser> users() {
        return userService.findAll();
    }

}
