package com.exia.nocvsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.Utils.TreeNode;
import com.exia.nocvsystem.Utils.TreeNodeBuilder;
import com.exia.nocvsystem.entity.Menu;
import com.exia.nocvsystem.entity.User;
import com.exia.nocvsystem.service.MenuService;
import com.exia.nocvsystem.service.RoleService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.MenuView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@RequestMapping("/menu")

public class MenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/toMenu")
    public String toMenu() {
        return "/menu/menu";
    }

    @ResponseBody
    @RequestMapping("/loadAllMenu")
    public DataView loadAllMenu(MenuView menuView) {
        IPage<Menu> page = new Page<>(menuView.getPage(), menuView.getLimit());
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(menuView.getTitle()), "title", menuView.getTitle());
        queryWrapper.orderByAsc("ordernum");
        menuService.page(page, queryWrapper);
        return new DataView(page.getTotal(), page.getRecords());
    }

    //加载父级菜单和左侧数据的dtree
    @RequestMapping("/loadMenuManagerLeftTreeJson")
    @ResponseBody
    public DataView loadMenuManagerLeftTreeJson() {
        List<Menu> list = menuService.list();
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Menu menu : list) {
            Boolean open = menu.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(menu.getId(), menu.getPid(), menu.getTitle(), open));
        }
        return new DataView(treeNodes);
    }

    //赋值最大的排序码
    //条件查询：倒叙排序，取一条数据+1
    @RequestMapping("/loadMenuMaxOrderNum")
    @ResponseBody
    public Map<String, Object> loadMenuMaxOrderNum() {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Menu> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("ordernum");
        IPage<Menu> page = new Page<>(1, 1);
        List<Menu> list = menuService.page(page, queryWrapper).getRecords();
        map.put("value", list.get(0).getOrdernum() + 1);
        return map;
    }

    //新增菜单逻辑
    @RequestMapping("/addMenu")
    @ResponseBody
    public DataView addMenu(Menu menu) {
        DataView dataView = new DataView();
        menu.setType("menu");
        boolean save = menuService.save(menu);
        if (!save) {
            dataView.setMsg("插入数据失败");
        }
        dataView.setMsg("菜单插入成功！");
        dataView.setCode(200);
        return dataView;
    }

    @ResponseBody
    @RequestMapping("/updateMenu")
    //更新菜单
    public DataView uppdateMenu(Menu menu) {
        menuService.updateById(menu);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("更新菜单成功！");
        return dataView;
    }

    /**
     * 判断有没有子类
     * 没有子类ID，可以删除
     */
    @ResponseBody
    @RequestMapping("/checkMenuHasChildrenNode")
    public Map<String, Object> checkMenuHasChildrenNode(Menu menu) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", menu.getId());
        List<Menu> list = menuService.list(queryWrapper);
        if (list.size() > 0) {
            map.put("value", true);
        } else {
            map.put("value", false);
        }
        return map;
    }

    /**
     * 真正的删除
     */
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public DataView deleteMenu(Menu menu) {
        menuService.removeById(menu.getId());
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除菜单成功！");
        return dataView;
    }

    /**
     * 加载主页面的index菜单栏，带有层级关系
     */
    @RequestMapping("/loadIndexLeftMenuJson")
    @ResponseBody
    public DataView loadIndexLeftMenuJson(Menu menu, HttpSession session) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        //查询的所有菜单栏 按照条件查询（管理员，替他 学生 老师 （条件查询））
        List<Menu> list = null;
        //1.取出Session中的用户ID
        User user = (User) session.getAttribute("user");
        Integer userId = user.getId();

            if (user.getUsername().equals("admin") || StringUtils.equals(user.getUsername(), "admin")) {
                list = menuService.list();
            } else {
                //2.根据用户ID查询角色ID
                List<Integer> currentUserRoleIds = roleService.queryUserRoleById(userId);
                //去重
                Set<Integer> mids = new HashSet<>();
                for (Integer rid : currentUserRoleIds) {
                    //根据角色ID查询菜单栏
                    List<Integer> permissionIds = roleService.queryAllPermissionByRid(rid);
                    //菜单栏ID和角色ID去重
                    mids.addAll(permissionIds);
                }
                if (mids.size() > 0) {
                    queryWrapper.in("id", mids);
                    list = menuService.list(queryWrapper);
                }
            }
            //根据角色ID
            List<TreeNode> treeNodes = new ArrayList<>();
            for (Menu m : list) {
                Integer id = m.getId();
                Integer pid = m.getPid();
                String title = m.getTitle();
                String icon = m.getIcon();
                String href = m.getHref();
                Boolean open = m.getOpen().equals(1) ? true : false;
                treeNodes.add(new TreeNode(id, pid, title, icon, href, open));

            }
            //层级关系
            List<TreeNode> nodeList = TreeNodeBuilder.build(treeNodes, 0);
            return new DataView(nodeList);

        }

}

