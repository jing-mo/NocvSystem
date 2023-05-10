package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.HeSuan;
import com.exia.nocvsystem.service.HeSuanService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.HeSuanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/22 21:34
 */
@Controller
@RequestMapping("/hesuan")
public class HeSuanController {
    @Autowired
    HeSuanService heSuanService;
    @RequestMapping("/toHeSuan")
    public String toHeSuan(){
        return "hesuan/hesuan";
    }
    @RequestMapping("/loadAllHeSuan")
    @ResponseBody
    public DataView loadAllHeSuan(HeSuanVo heSuanVo){
        IPage<HeSuan> page=new Page<>(heSuanVo.getPage(),heSuanVo.getLimit());
        QueryWrapper<HeSuan> queryWrapper=new QueryWrapper();

        queryWrapper.like(!(heSuanVo.getName() == null), "name", heSuanVo.getName());

        heSuanService.page(page,queryWrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }
    @RequestMapping("/addHeSuan")
    @ResponseBody
    public DataView addHeSuan(HeSuan heSuan){
        heSuanService.save(heSuan);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("核酸信息添加成功");
        return dataView;
    }
    @RequestMapping("/deleteHeSuan")
    @ResponseBody
    public DataView deleteHeSuan(HeSuan heSuan){
        heSuanService.removeById(heSuan.getId());
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("核酸信息删除成功");
        return dataView;
    }
    @RequestMapping("/updateHeSuan")
    @ResponseBody
    public DataView updateHeSuan(HeSuan heSuan){
        heSuanService.updateById(heSuan);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("核酸信息修改成功");
        return dataView;
    }
}
