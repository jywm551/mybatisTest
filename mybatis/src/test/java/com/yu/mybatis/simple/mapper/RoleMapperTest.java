package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.SysPrivilege;
import com.yu.mybatis.simple.model.SysRole;
import com.yu.mybatis.simple.plugin.PageRowBounds;
import com.yu.mybatis.simple.type.Enabled;
import org.apache.ibatis.session.RowBounds;
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

    @Test
    public void testSelectRoleByUserIdChoose() {
        try (SqlSession session = getSqlSessionFactory()) {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            SysRole sysRole = mapper.selectRoleById(2L);
            sysRole.setEnabled(Enabled.disabled);
            mapper.updateById(sysRole);
            List<SysRole> sysRoleList = mapper.selectRoleByUserIdChoose(1L);
            for (SysRole role : sysRoleList) {
                System.out.println("角色名：" + role.getRoleName());
                if (role.getId().equals(1L)) {
                    Assert.assertNotNull(role.getPrivilegeList());
                } else if (role.getId().equals(2L)) {
                    Assert.assertNull(role.getPrivilegeList());
                    continue;
                }
                for (SysPrivilege p : role.getPrivilegeList()) {
                    System.out.println("权限名" + p.getPrivilegeName());
                }
            }
        }
    }

    @Test
    public void testUpdateById() {
        SqlSession session = getSqlSessionFactory();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            SysRole sysRole = mapper.selectById(2L);
            Assert.assertEquals(Enabled.enabled, sysRole.getEnabled());
            sysRole.setEnabled(Enabled.disabled);
            mapper.updateById(sysRole);

        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testSelectAllByRowBounds() {
        try (SqlSession session = getSqlSessionFactory()) {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            // 查询第一个，使用RowBounds 类型时不糊查询总数
            RowBounds bounds = new RowBounds(0, 1);
            List<SysRole> sysRoleList = mapper.selectAll(bounds);
            for (SysRole role : sysRoleList) {
                System.out.println("角色名：" + role.getRoleName());
            }
            // 使用PageRowBounds时会查询总数
            PageRowBounds pageRowBounds = new PageRowBounds(0, 1);
            sysRoleList = mapper.selectAll(pageRowBounds);
            // 获取总数
            System.out.println("查询总数:" + pageRowBounds.getTotal());
            for (SysRole role : sysRoleList) {
                System.out.println("角色名：" + role.getRoleName());
            }
            // 再次查询第二个角色
            pageRowBounds  = new PageRowBounds(1,1);
            sysRoleList = mapper.selectAll(pageRowBounds);
            // 获取总数
            System.out.println("查询总数:" + pageRowBounds.getTotal());
            for (SysRole role : sysRoleList) {
                System.out.println("角色名：" + role.getRoleName());
            }
        }
    }

}
