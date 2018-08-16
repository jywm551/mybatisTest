package com.yu.mybatis.springboot.service.impl;

import com.yu.mybatis.simple.mapper.UserMapper;
import com.yu.mybatis.simple.model.SysUser;
import com.yu.mybatis.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By Yu On 2018/8/16
 * Description：
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public SysUser findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<SysUser> findAll() {
        return userMapper.selectAll();
    }
}
