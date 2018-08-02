package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysRole;
import com.yu.mybatis.simple.model.SysUser;

import java.util.List;

public interface UserMapper {
    SysUser selectById(Long id);

    List<SysUser> selectAll();

    List<SysRole> selectRoleByUserId(Long id);
}
