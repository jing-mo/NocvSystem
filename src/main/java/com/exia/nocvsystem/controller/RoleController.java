package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.Utils.TreeNode;
import com.exia.nocvsystem.entity.Menu;
import com.exia.nocvsystem.entity.Role;
import com.exia.nocvsystem.service.MenuService;
import com.exia.nocvsystem.service.RoleService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.RoleView;
import java.io.Serializable;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.ibatis.ognl.DynamicSubscript.mid;


/**
 * @author exia
 * @version 1.0
 * Create by 2023/3/16 10:24
 */
@Controller
@RequestMapping("/role")
public class RoleController implements Serializable {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @RequestMapping("/toRole")
    public String toRole(){
        return "role/role";
    }
    /**
     * 查询所有的角色带有分页，带有查询条件
     */
    @RequestMapping("/loadAllRole")
    @ResponseBody
    public DataView loadAllRole(RoleView roleView){
        IPage<Role> page=new Page<>(roleView.getPage(),roleView.getLimit());
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleView.getName()),"name",roleView.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleView.getRemark()),"remark",roleView.getRemark());
        roleService.page(page,queryWrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }
    /**
     * 添加角色
     */
    @RequestMapping("/addRole")
    @ResponseBody
    public DataView addRole(Role role){
        roleService.save(role);
        DataView dataView=new DataView();
        dataView.setMsg("添加角色成功！");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 删除角色信息
     * @param role
     * @return
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public DataView deleteRole(Role role){
        roleService.removeById(role.getId());
        DataView dataView=new DataView();
        dataView.setMsg("删除角色成功！");
        dataView.setCode(200);
        return dataView;
    }
    @RequestMapping("/updateRole")
    @ResponseBody
    public DataView updateRole(Role role){
        roleService.saveOrUpdate(role);
        DataView dataView=new DataView();
        dataView.setMsg("修改角色成功！");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 初始化下拉列表的具有权限
     * 根据角色查询菜单
     * @param roleId
     * @return
     */

    @RequestMapping("/initPermissionByRoleId")
    @ResponseBody
    public DataView initPermissonByRoleId(Integer roleId) {
        //1.把菜单的权限查出来
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<Menu>();
        List<Menu> allPermissions = menuService.list();
        //2.根据角色rid【roleId】查询所有的菜单mid
        List<Integer> currentRolePermissions = roleService.queryPermissionByRid(roleId);
        //3.rid mid所有的ID，去查询菜单和角色的数据
        List<Menu> menus=null;
        //4.查询到mid rid
        if(currentRolePermissions.size()>0){
            queryWrapper.in("id",currentRolePermissions);
            menus = menuService.list(queryWrapper);
        }else{
            menus=new ArrayList<>();
        }
        //5.返回前台的格式，带有层级关系TreeNode
        List<TreeNode> nodes=new ArrayList<>();
        for(Menu allPermission : allPermissions) {
            String checkArr = "0";
            for(Menu currentPermission : menus)
            if(allPermission.getId().equals(currentPermission.getId())) {
                checkArr = "1";
                break;
            }
            Boolean spread=(allPermission.getOpen()==null||allPermission.getOpen()==1)?true:false;
            nodes.add(new TreeNode(allPermission.getId(),allPermission.getPid(),allPermission.getTitle(),spread,checkArr));
        }
        return new DataView(nodes);
    }
    /**
     * 点击确认分配权限
     * 插入数据库表
     */
    @RequestMapping("/saveRolePermission")
    @ResponseBody
    public DataView saveRolePermission(Integer rid,Integer[] mids){
        //1.根据rid删除之前的mid权限
        roleService.deleteRoleByRid(rid);
        //2.保存权限
        if(mids!=null && mids.length>0){
            for (Integer mid:mids){
                roleService.saveRoleMenu(rid,mid);
            }

        }
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("菜单权限分配成功！");
        return dataView;
    }
}
