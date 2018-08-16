package com.yu.mybatis.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By Yu On 2018/8/16
 * Description：
 **/
@RestController
public class IndexController {
    @RequestMapping("/")
    String home(){
        return "Hello world!!!";
    }
}
