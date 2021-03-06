package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysRole;
import com.yu.mybatis.simple.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    SysUser selectById(Long id);

    List<SysUser> selectAll();

    List<SysRole> selectRoleByUserId(Long id);

    int insert(SysUser sysUser);

    int insert2(SysUser sysUser);

//    int insert3(SysUser sysUser);

    int updateById(SysUser sysUser);

    int deleteById(Long id);

    int deleteById(SysUser sysUser);

    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId, @Param("enabled") Integer enabled);

    List<SysUser> selectByUser(SysUser sysUser);

    int updateBySelective(SysUser sysUser);

    SysUser selectByIdOrUserName(SysUser sysUser);

    List<SysUser> selectByIdList(List<Long> idList);

    int insertList(List<SysUser> userList);

    void updateByMap(Map<String, Object> map);

    SysUser selectUserAndRoleById(Long id);

    SysUser selectUserAndRoleByIdSelect(Long id);

    List<SysUser> selectAllUserAndRoles();

    SysUser selectAllUserAndRolesSelect(Long id);

    void selectUserById(SysUser sysUser);

    List<SysUser> selectUserPage(Map<String, Object> params);

    void insertUserAndRoles(@Param("user") SysUser sysUser, @Param("roleIds") String roleIds);

    void deleteUserById(Long id);
}
