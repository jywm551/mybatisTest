package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysRole;
import com.yu.mybatis.simple.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.*;

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
        try {
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
        } finally {
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

    @Test
    public void testUpdateById() {
        SqlSession session = getSqlSessionFactory();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            SysUser user = mapper.selectById(1L);
            Assert.assertEquals("admin", user.getUserName());
            user.setUserName("admin_test");
            user.setUserEmail("test@mybatis.com");
            int i = mapper.updateById(user);
            Assert.assertEquals(1, i);
            user = mapper.selectById(1L);
            Assert.assertEquals("admin_test", user.getUserName());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testDeleteById() {
        SqlSession session = getSqlSessionFactory();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            SysUser user = mapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals(1, mapper.deleteById(1L));
            Assert.assertNull(mapper.selectById(1L));

            SysUser user1 = mapper.selectById(1001L);
            Assert.assertNotNull(user1);
            Assert.assertEquals(1, mapper.deleteById(user1));
            Assert.assertNull(mapper.selectById(1001L));
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testSelectRolesByUserIdAndRoleEnabled() {
        try (SqlSession session = getSqlSessionFactory()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<SysRole> sysRoles = mapper.selectRolesByUserIdAndRoleEnabled(1L, 1);

            Assert.assertNotNull(sysRoles);
            Assert.assertTrue(sysRoles.size() > 0);
        }
    }

    @Test
    public void testProxy() {

        SqlSession session = getSqlSessionFactory();
        MyMapperProxy<UserMapper> userMapperMyMapperProxy = new MyMapperProxy<>(UserMapper.class, session);

        UserMapper o = (UserMapper) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{UserMapper.class},
                userMapperMyMapperProxy);

        List<SysUser> sysUsers = o.selectAll();
        Assert.assertNotNull(sysUsers);
        Assert.assertTrue(sysUsers.size() > 0);
    }

    @Test
    public void testSelectByUser() {
        try (SqlSession session = getSqlSessionFactory()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setUserName("ad");
            List<SysUser> sysUsers = mapper.selectByUser(sysUser);
            Assert.assertTrue(sysUsers.size() > 0);

            sysUser = new SysUser();
            sysUser.setUserEmail("test@mybatis.com");
            sysUsers = mapper.selectByUser(sysUser);
            Assert.assertTrue(sysUsers.size() > 0);

            sysUser = new SysUser();
            sysUser.setUserName("ad");
            sysUser.setUserEmail("test@mybatis.com");
            sysUsers = mapper.selectByUser(sysUser);
            Assert.assertTrue(sysUsers.size() == 0);

        }
    }

    @Test
    public void testUpdateBySelective() {
        SqlSession session = getSqlSessionFactory();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setUserEmail("test@mybatis.com");
            int result = mapper.updateBySelective(sysUser);
            Assert.assertEquals(1, result);

            sysUser = mapper.selectById(1L);

            Assert.assertEquals("admin", sysUser.getUserName());
            Assert.assertEquals("test@mybatis.com", sysUser.getUserEmail());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testInsert2Selective() {
        SqlSession session = getSqlSessionFactory();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test-selective");
            sysUser.setUserPassword("123456");
            sysUser.setUserInfo("test info");
            sysUser.setCreateTime(new Date());
            mapper.insert2(sysUser);

            sysUser = mapper.selectById(sysUser.getId());
            Assert.assertEquals("test@mybatis.com", sysUser.getUserEmail());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testSelectByIdOrUserName() {
        try (SqlSession session = getSqlSessionFactory()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            SysUser sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setUserName("admin");
            SysUser sysUser1 = mapper.selectByIdOrUserName(sysUser);
            Assert.assertNotNull(sysUser1);

            sysUser.setId(null);
            sysUser1 = mapper.selectByIdOrUserName(sysUser);
            Assert.assertNotNull(sysUser1);

            sysUser.setUserName(null);
            sysUser1 = mapper.selectByIdOrUserName(sysUser);
            Assert.assertNull(sysUser1);
        }
    }

    @Test
    public void testSelectByIdList() {
        try (SqlSession session = getSqlSessionFactory()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<Long> idList = new ArrayList<>();
            idList.add(1L);
            idList.add(1001L);
            List<SysUser> userList = mapper.selectByIdList(idList);
            Assert.assertEquals(2, userList.size());
        }
    }

    @Test
    public void testInsertList() {
        SqlSession session = getSqlSessionFactory();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);

            List<SysUser> userList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                SysUser sysUser = new SysUser();
                sysUser.setUserName("test" + i);
                sysUser.setUserPassword("123456");
                sysUser.setUserEmail("test@mybatis.com");
                userList.add(sysUser);
            }
            int result = mapper.insertList(userList);
            Assert.assertEquals(2, result);
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testUpdateByMap(){
        SqlSession session = getSqlSessionFactory();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            Map<String ,Object> map = new HashMap<>();
            map.put("id",1L);
            map.put("user_email","test@mybatis.com");
            map.put("user_password","12345678");

            mapper.updateByMap(map);
            SysUser sysUser = mapper.selectById(1L);
            Assert.assertEquals("test@mybatis.com",sysUser.getUserEmail());

        }finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testSelectUserAndRoleById(){
        try (SqlSession session =getSqlSessionFactory()){
            UserMapper mapper = session.getMapper(UserMapper.class);
            SysUser sysUser = mapper.selectUserAndRoleById(1001L);
            Assert.assertNotNull(sysUser);
            Assert.assertNotNull(sysUser.getRole());

        }
    }
}
