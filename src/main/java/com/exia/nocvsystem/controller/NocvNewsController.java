package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.NocvNews;
import com.exia.nocvsystem.service.NocvNewsService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.NocvNewsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/17 22:57
 */
@Controller
@RequestMapping("/news")
public class NocvNewsController {
    @Autowired
    private NocvNewsService nocvNewsService;
    @RequestMapping("/toNews")
    private String toNews(){
        return "news/news";
    }
    @RequestMapping("/listNews")
    @ResponseBody
    public DataView listNews(NocvNewsVo nocvNewsVo){
        QueryWrapper<NocvNews> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(nocvNewsVo.getTitle()),"title",nocvNewsVo.getTitle());
        IPage<NocvNews> ipage=new Page<>(nocvNewsVo.getPage(), nocvNewsVo.getLimit());
        nocvNewsService.page(ipage,queryWrapper);
        return new DataView(ipage.getTotal(),ipage.getRecords());
    }
    @ResponseBody
    @RequestMapping("/deleteById")
    public DataView deleteById(Integer id){
        nocvNewsService.autoIncrement();
        nocvNewsService.removeById(id);
        DataView dataView=new DataView();
        dataView.setMsg("删除新闻成功！");
        dataView.setCode(200);
        return dataView;
    }
    @ResponseBody
    @RequestMapping("/addOrUpdateNews")
    public DataView addOrUpdateNews(NocvNews nocvNews){
        nocvNewsService.autoIncrement();
        nocvNews.setCreateTime(new Date());
        nocvNewsService.saveOrUpdate(nocvNews);
        DataView dataView=new DataView();
        dataView.setMsg("添加新闻成功！");
        dataView.setCode(200);
        return dataView;
    }
}
