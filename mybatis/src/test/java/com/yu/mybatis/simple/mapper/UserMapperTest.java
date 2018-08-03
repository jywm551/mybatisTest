package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysRole;
import com.yu.mybatis.simple.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
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

    @Test
    public void testInsert() {
        SqlSession session = getSqlSessionFactory();
        try  {
            UserMapper mapper = session.getMapper(UserMapper.class);

            SysUser sysUser = new SysUser();
            sysUser.setUserName("test1");
            sysUser.setUserPassword("123456");
            sysUser.setUserEmail("test@mybatis.com");
            sysUser.setUserInfo("test info");
            sysUser.setHeadImg(new byte[]{1, 2, 3});
            sysUser.setCreateTime(new Date());

            int insert = mapper.insert(sysUser);

            Assert.assertEquals(1, insert);
            Assert.assertNull(sysUser.getId());
        }finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testInsert2() {
        SqlSession session = getSqlSessionFactory();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);

            SysUser sysUser = new SysUser();
            sysUser.setUserName("test1");
            sysUser.setUserPassword("123456");
            sysUser.setUserEmail("test@mybatis.com");
            sysUser.setUserInfo("test info");
            sysUser.setHeadImg(new byte[]{1, 2, 3});
            sysUser.setCreateTime(new Date());

            int result = mapper.insert2(sysUser);

            Assert.assertEquals(1, result);
            Assert.assertNotNull(sysUser.getId());

        } finally {
            session.rollback();
            session.close();
        }
    }
}
