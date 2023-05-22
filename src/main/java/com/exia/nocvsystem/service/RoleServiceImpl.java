package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exia.nocvsystem.dao.RoleMapper;
import com.exia.nocvsystem.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/3/16 10:26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Integer> queryPermissionByRid(Integer roleId) {
        return roleMapper.queryMidByRid(roleId);
    }

    @Override
    public void deleteRoleByRid(Integer rid) {
        roleMapper.deleteRoleByRid(rid);
    }

    @Override
    public void saveRoleMenu(Integer rid, Integer mid) {
        roleMapper.saveRoleMenu(rid,mid);
    }
    //根据用户id查询所有的角色
    @Override
    public List<Integer> queryUserRoleById(Integer id) {
        return  roleMapper.queryUserRoleById(id);
    }

    @Override
    public List<Integer> queryAllPermissionByRid(Integer userId) {
        return roleMapper.queryAllPermissionByRid(userId);
    }

    @Override
    public void autoIncrement() {
        roleMapper.autoIncrement();
    }


}
