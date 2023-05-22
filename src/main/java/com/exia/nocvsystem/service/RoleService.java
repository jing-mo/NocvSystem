package com.exia.nocvsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exia.nocvsystem.dao.RoleMapper;
import com.exia.nocvsystem.entity.Role;
import org.apache.ibatis.ognl.DynamicSubscript;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Integer> queryPermissionByRid(Integer roleId);

    void deleteRoleByRid(Integer rid);

    void saveRoleMenu(Integer rid, Integer mid);

    List<Integer> queryUserRoleById(Integer id);


    List<Integer> queryAllPermissionByRid(Integer userId);
    public void autoIncrement();
}
