package com.yu.mybatis.simple.model;

/**
 * Created By Yu On 2018/8/2
 * Description：
 **/
public class SysUserRole {

    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
