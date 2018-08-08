package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysPrivilege;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created By Yu On 2018/8/8
 * Description：
 **/
public class PrivilegeMapperTest extends BaseMapperTest{

    @Test
    public void testSelectById(){
        try(SqlSession session = getSqlSessionFactory()){
            PrivilegeMapper mapper = session.getMapper(PrivilegeMapper.class);
            SysPrivilege sysPrivilege = mapper.selectById(1L);
            Assert.assertNotNull(sysPrivilege);
            Assert.assertEquals("用户管理",sysPrivilege.getPrivilegeName());

        }
    }
}
