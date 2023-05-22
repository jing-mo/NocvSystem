package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.Institude;
import com.exia.nocvsystem.entity.NocvNews;
import com.exia.nocvsystem.service.InstitudeService;
import com.exia.nocvsystem.service.NocvNewsService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.InstitudeVo;
import com.exia.nocvsystem.vo.NocvNewsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/18 0:44
 */
@Controller
@RequestMapping("/institude")
public class InstitudeController {
    @Autowired
    private InstitudeService institudeService;
    @RequestMapping("/toInstitude")
    private String toInstitude(){
        return "institude/institude";
    }
    @RequestMapping("/listInstitudes")
    @ResponseBody
    public DataView listNews(InstitudeVo institudeVo){
        QueryWrapper<Institude> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(institudeVo.getName()),"name",institudeVo.getName());
        IPage<Institude> ipage=new Page<>(institudeVo.getPage(), institudeVo.getLimit());
        institudeService.page(ipage,queryWrapper);
        return new DataView(ipage.getTotal(),ipage.getRecords());
    }
    @ResponseBody
    @RequestMapping("/deleteById")
    public DataView deleteById(Integer id){
        institudeService.autoIncrement();
        institudeService.removeById(id);
        DataView dataView=new DataView();
        dataView.setMsg("删除院系信息成功！");
        dataView.setCode(200);
        return dataView;
    }
    @ResponseBody
    @RequestMapping("/addOrUpdateInstitude")
    public DataView addOrUpdateNews(Institude institude){
        institudeService.autoIncrement();
        institudeService.saveOrUpdate(institude);
        DataView dataView=new DataView();
        dataView.setMsg("添加院系信息成功！");
        dataView.setCode(200);
        return dataView;
    }
}
