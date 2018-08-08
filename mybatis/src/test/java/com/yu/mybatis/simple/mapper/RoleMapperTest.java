package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysRole;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created By Yu On 2018/8/8
 * Description：
 **/
public class RoleMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        try (SqlSession session = getSqlSessionFactory()) {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            SysRole sysRole = mapper.selectById(1L);
            Assert.assertNotNull(sysRole);
            Assert.assertEquals("管理员", sysRole.getRoleName());
        }
    }

    @Test
    public void testSelectById2() {
        try (SqlSession session = getSqlSessionFactory()) {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            SysRole sysRole = mapper.selectById2(1L);
            Assert.assertNotNull(sysRole);
            Assert.assertEquals("管理员", sysRole.getRoleName());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = getSqlSessionFactory()) {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            List<SysRole> sysRoles = mapper.selectAll();
            Assert.assertNotNull(sysRoles);
            Assert.assertTrue(sysRoles.size() > 0);
            Assert.assertNotNull(sysRoles.get(0).getRoleName());
        }
    }
}
