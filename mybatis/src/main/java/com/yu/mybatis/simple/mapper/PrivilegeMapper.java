package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysPrivilege;
import com.yu.mybatis.simple.provider.PrivilegeProvider;
import org.apache.ibatis.annotations.SelectProvider;

public interface PrivilegeMapper {

    @SelectProvider(type = PrivilegeProvider.class,method = "selectById")
    SysPrivilege selectById(Long id);
}
