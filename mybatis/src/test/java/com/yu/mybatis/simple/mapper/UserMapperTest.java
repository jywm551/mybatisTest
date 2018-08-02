package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysRole;
import com.yu.mybatis.simple.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        try (SqlSession session = getSqlSessionFactory()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser sysUser = userMapper.selectById(1L);
            Assert.assertNotNull(sysUser);
            Assert.assertEquals("admin", sysUser.getUserName());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = getSqlSessionFactory()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<SysUser> sysUsers = mapper.selectAll();
            Assert.assertNotNull(sysUsers);
            Assert.assertTrue(sysUsers.size() > 0);
        }
    }

    @Test
    public void testSelectRoleByUserId() {
        try (SqlSession session = getSqlSessionFactory()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<SysRole> sysRole = mapper.selectRoleByUserId(1L);
            Assert.assertNotNull(sysRole);
            Assert.assertTrue(sysRole.size() > 0);
        }
    }
}
