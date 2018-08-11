package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysRole;
import com.yu.mybatis.simple.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

public class CacheTest extends BaseMapperTest {

    @Test
    public void testL1Cache() {
        SysUser user;
        try (SqlSession session = getSqlSessionFactory()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.selectById(1L);
            user.setUserName("New Name");
            SysUser user2 = mapper.selectById(1L);
            Assert.assertEquals("New Name", user2.getUserName());
            Assert.assertEquals(user, user2);
        }
        System.out.println("开启新的SqlSession");

        try (SqlSession session = getSqlSessionFactory()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            SysUser user2 = mapper.selectById(1L);
            Assert.assertNotEquals("New Name", user2.getUserName());
            Assert.assertNotEquals(user, user2);
            mapper.deleteById(2L);
            SysUser user3 = mapper.selectById(1L);
            Assert.assertNotEquals(user2,user3);

        }
    }

    @Test
    public void testL2Cache() {
        SysRole role;
        try (SqlSession session = getSqlSessionFactory()) {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            role = mapper.selectById(1L);
            role.setRoleName("New Name");
            SysRole role2 = mapper.selectById(1L);
            Assert.assertEquals("New Name", role2.getRoleName());
            Assert.assertEquals(role, role2);
        }
        System.out.println("开启新的SqlSession");

        try (SqlSession session = getSqlSessionFactory()) {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            SysRole role2 = mapper.selectById(1L);
            Assert.assertEquals("New Name", role.getRoleName());

            Assert.assertNotEquals(role, role2);
            SysRole role3 = mapper.selectById(1L);
            Assert.assertNotEquals(role2,role3);
        }
    }
}
