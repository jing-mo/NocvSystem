package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.Class;
import com.exia.nocvsystem.entity.Institude;
import com.exia.nocvsystem.service.ClassService;
import com.exia.nocvsystem.service.InstitudeService;
import com.exia.nocvsystem.vo.ClassVo;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.InstitudeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/18 7:58
 */
@RequestMapping("/class")
@Controller
public class ClassController {
    @Autowired
    private ClassService classService;
    @RequestMapping("/toClass")
    private String toClass(){
        return "class/class";
    }
    @RequestMapping("/listClass")
    @ResponseBody
    public DataView listNews(ClassVo classVo){
        QueryWrapper<Class> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(classVo.getName()),"name",classVo.getName());
        IPage<Class> ipage=new Page<>(classVo.getPage(), classVo.getLimit());
        classService.page(ipage,queryWrapper);
        return new DataView(ipage.getTotal(),ipage.getRecords());
    }
    @ResponseBody
    @RequestMapping("/deleteById")
    public DataView deleteById(Integer id){
        classService.removeById(id);
        DataView dataView=new DataView();
        dataView.setMsg("删除班级信息成功！");
        dataView.setCode(200);
        return dataView;
    }
    @ResponseBody
    @RequestMapping("/addOrUpdateClass")
    public DataView addOrUpdateNews(Class c){
        classService.saveOrUpdate(c);
        DataView dataView=new DataView();
        dataView.setMsg("添加院系信息成功！");
        dataView.setCode(200);
        return dataView;
    }
}
