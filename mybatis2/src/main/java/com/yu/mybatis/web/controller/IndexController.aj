package com.yu.mybatis.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 *
 * Created By Yu On 2018/8/14
 * Description：
 *
 **/
@Controller
public class IndexController {

    @RequestMapping(value = {"","/index"})
    public ModelAndView dicts(){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("now",new Date());
        return mv;
    }
}
