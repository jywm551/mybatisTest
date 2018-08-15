package com.yu.mybatis.web.service;

import com.yu.mybatis.web.model.SysDict;

import java.util.List;

/**
 * Created By Yu On 2018/8/14
 * Description：
 **/
public interface DictService {
    SysDict findById(Long id);

    List<SysDict> findBySysDict(SysDict sysDict,Integer offset,Integer limit);

    boolean saveOrUpdate(SysDict sysDict);

    boolean deleteById(Long id);
}
