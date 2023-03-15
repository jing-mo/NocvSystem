package com.exia.nocvsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.Menu;
import com.exia.nocvsystem.service.MenuService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.MenuView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;
    @RequestMapping("/toMenu")
    public String toMenu(){
        return "/menu/menu";
    }
    @ResponseBody
    @RequestMapping("/loadAllMenu")
    public DataView loadAllMenu(MenuView menuView){
        IPage<Menu> page=new Page<>(menuView.getPage(),menuView.getLimit());
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(menuView.getTitle()),"title",menuView.getTitle());
        queryWrapper.orderByAsc("ordernum");
        menuService.page(page);
        return new DataView(page.getTotal(),page.getRecords());
    }
}
