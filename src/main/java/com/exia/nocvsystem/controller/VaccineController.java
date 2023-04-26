package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.HeSuan;
import com.exia.nocvsystem.entity.Vaccine;
import com.exia.nocvsystem.service.VaccineService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.HeSuanVo;
import com.exia.nocvsystem.vo.VaccineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/23 9:49
 */
@Controller
@RequestMapping("/vaccine")
public class VaccineController {
    @Autowired
    VaccineService vaccineService;
    @RequestMapping("/toVaccine")
    public String toVaccine(){
        return "vaccine/vaccine";
    }
    @RequestMapping("/loadAllVaccine")
    @ResponseBody
    public DataView loadAllVaccine(VaccineVo vaccineVo){
        IPage<Vaccine> page=new Page<>(vaccineVo.getPage(),vaccineVo.getLimit());
        QueryWrapper<Vaccine> queryWrapper=new QueryWrapper();
        vaccineService.page(page,queryWrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }
    @RequestMapping("/addVaccine")
    @ResponseBody
    public DataView addVaccine(Vaccine vaccine){
        vaccineService.save(vaccine);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("疫苗信息添加成功");
        return dataView;
    }
    @RequestMapping("/deleteVaccine")
    @ResponseBody
    public DataView deleteVaccine(Vaccine vaccine){
        vaccineService.removeById(vaccine.getId());
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("疫苗信息删除成功");
        return dataView;
    }
    @RequestMapping("/updateVaccine")
    @ResponseBody
    public DataView updateVaccine(Vaccine vaccine){
        vaccineService.updateById(vaccine);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("核酸信息修改成功");
        return dataView;
    }
}
