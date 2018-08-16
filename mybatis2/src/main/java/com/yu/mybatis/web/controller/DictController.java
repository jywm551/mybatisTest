package com.yu.mybatis.web.controller;

import com.yu.mybatis.web.model.SysDict;
import com.yu.mybatis.web.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created By Yu On 2018/8/14
 * Description：
 **/
@Controller
@RequestMapping("/dicts")
public class DictController {
    @Autowired
    private DictService dictService;

    @RequestMapping
    public ModelAndView dicts(SysDict sysDict,Integer offset,Integer limit){
        ModelAndView mv = new ModelAndView("dict");
        List<SysDict> dictList = dictService.findBySysDict(sysDict, offset, limit);
        mv.addObject("dictList",dictList);
        return mv;
    }

    @RequestMapping(value = "add",method = RequestMethod.GET)
    public ModelAndView add(Long id){
        ModelAndView mv = new ModelAndView("dict_add");
        SysDict sysDict;
        if (id==null){
            sysDict = new SysDict();
        }else {
            sysDict = dictService.findById(id);
        }
        mv.addObject("model",sysDict);
        return mv;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public ModelAndView save(SysDict sysDict){
        ModelAndView mv = new ModelAndView();
        try {
            dictService.saveOrUpdate(sysDict);
            mv.setViewName("redirect:/dict");
        }catch (Exception e){
            mv.setViewName("dict_add");
            mv.addObject("msg",e.getMessage());
            mv.addObject("model",sysDict);
        }
        return mv;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap delete(@RequestParam Long id){
        ModelMap mp = new ModelMap();
        try {
            boolean delete = dictService.deleteById(id);
            mp.put("success",delete);
        }catch (Exception e){
            mp.put("success",false);
            mp.put("msg",e.getMessage());
        }
        return mp;
    }

}
