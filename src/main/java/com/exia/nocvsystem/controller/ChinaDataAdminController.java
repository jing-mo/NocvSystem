package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.NocvData;
import com.exia.nocvsystem.service.IndexService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.NocvDataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChinaDataAdminController {
    @Autowired
    private IndexService indexService;
    @RequestMapping("toChinaManager")
    public String toChinaManager(){
        return "admin/chinadatamanager";
    }
    @RequestMapping("/listDataByPage")
    @ResponseBody
    public DataView listDataByPage(NocvDataView nocvDataView){
        //1.创建分页的对象 当前页 每页限制大小
        IPage<NocvData> page=new Page<>(nocvDataView.getPage(), nocvDataView.getLimit());
        //2.模糊查询条件
        QueryWrapper<NocvData> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!(nocvDataView.getName()==null),"name",nocvDataView.getName());
        //3.疫情数据确诊最多的排在上面
        queryWrapper.orderByDesc("value");
        //4.查询数据库
        indexService.page(page,queryWrapper);
        //5.返回数据封装
        DataView dataView=new DataView(page.getTotal(),page.getRecords());
        return dataView;
    }
}
