package com.yu.mybatis.springboot.service;

import com.yu.mybatis.simple.model.SysUser;

import java.util.List;

/**
 * Created By Yu On 2018/8/16
 * Description：
 **/
public interface UserService {
    SysUser findById(Long id);

    List<SysUser> findAll();


}
