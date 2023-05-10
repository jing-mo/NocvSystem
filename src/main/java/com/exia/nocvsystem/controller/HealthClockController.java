package com.exia.nocvsystem.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.HealthClock;
import com.exia.nocvsystem.service.HealthClockService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.HealthClockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthClockController extends BaseController{
    @Autowired
    private HealthClockService healthClockService;
    //跳转页面
    @RequestMapping("/toHealthClock")
    public String toHealthClock(){
        return "admin/healthclock";
    }
    /*
     *查询所有打卡记录 带有模糊查询条件 带有分页
     *@param healthClockVo
     *@return
     */
    @RequestMapping("/listHealthClock")
    @ResponseBody
    public DataView listHealthClock(HealthClockVo healthClockVo){
        //查询所有带有模糊查询条件 带有分页
        IPage<HealthClock> page=new Page<>(healthClockVo.getPage(),healthClockVo.getLimit());
        QueryWrapper<HealthClock> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(healthClockVo.getUsername()),"username",healthClockVo.getUsername());
        queryWrapper.eq(ObjectUtil.isNotEmpty(healthClockVo.getPhone()),"phone",healthClockVo.getPhone());
        addCard(queryWrapper);
        healthClockService.page(page,queryWrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }
    @RequestMapping("/addHealthClock")
    @ResponseBody
    public DataView addHealthClock(HealthClock healthClock){
        boolean save=healthClockService.saveOrUpdate(healthClock);
        DataView dataView=new DataView();
        if(save){
            dataView.setCode(200);
            dataView.setMsg("新增健康打卡数据成功！");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("新增健康打卡数据失败！");
        return dataView;
    }
    @RequestMapping("/deleteHealthClockById")
    @ResponseBody
    public DataView deleteHealthClockById(Integer id){
        healthClockService.removeById(id);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除健康打卡数据成功！");
        return dataView;
    }

}
