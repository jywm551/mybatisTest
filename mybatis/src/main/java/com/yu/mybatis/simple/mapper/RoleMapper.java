package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@CacheNamespace(
//        eviction = FifoCache.class,
//        flushInterval = 60000,
//        size = 512,
//        readWrite = false
//)
@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {


    @Results(id = "roleResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select({"select id,role_name,enabled,create_by,create_time from sys_role where id=#{id}"})
    SysRole selectById(Long id);

    //    @ResultMap("roleResultMap")
//    @Select({"select * from sys_role where id = #{id}"})
    SysRole selectRoleById(Long id);

    @Select({"select * from sys_role where id=#{id}"})
    SysRole selectById2(Long id);

    @ResultMap("roleResultMap")
    @Select("select * from sys_role")
    List<SysRole> selectAll();



    @Update("update sys_role " +
            "set role_name=#{roleName} , enabled=#{enabled} , create_by = #{createBy} ," +
            " create_time = #{createTime , jdbcType = TIMESTAMP} where id = #{id}")
    void updateById(SysRole sysRole);


    List<SysRole> selectRoleByUserIdChoose(Long userId);

}
