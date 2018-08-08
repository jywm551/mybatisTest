package com.yu.mybatis.simple.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * Created By Yu On 2018/8/8
 * Description：
 **/
public class PrivilegeProvider {

    public String selectById(final Long id) {
        return new SQL() {
            {
                SELECT("id,privilege_name,privilege_url");
                FROM("sys_privilege");
                WHERE("Id = #{id}");
            }
        }.toString();
    }
}
