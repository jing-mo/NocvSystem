package com.exia.nocvsystem.controller;

import com.exia.nocvsystem.entity.LineTrend;
import com.exia.nocvsystem.entity.NocvData;
import com.exia.nocvsystem.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;
    @RequestMapping("/query")
    @ResponseBody
    public List<NocvData> queryData(){
        List<NocvData> list=indexService.list();
        return list;
    }
    //跳转pie页面
    @RequestMapping("/toPie")
    public String toPie(){
        return "Pie";
    }
    /**
     * 分组聚合
     *SQL: select count(*) form foods group by tyoe
     * **/
    @RequestMapping("/queryPie")
    @ResponseBody
    public List<NocvData> queryPieData(){
        List<NocvData> list=indexService.list();
        return list;
    }
    /**
     * 跳转柱状图界面
     * **/
    @RequestMapping("/toBar")
    public String toBar(){
        return "bar";
    }
    @RequestMapping("/queryBar")
    @ResponseBody
    public Map<String,List<Object>> queryBarData(){
        //1.所有城市数据:数值
        List<NocvData> list=indexService.list();
        //2.所有城市数据
        List<String> cityList=new ArrayList<>();
        for(NocvData data: list){
            cityList.add(data.getName());
        }
        //3.所有疫情数据
        List<Integer> dataList=new ArrayList<>();
        for(NocvData data: list){
            dataList.add(data.getValue());
        }
        //4.创建一个容器
        Map map=new HashMap();
        map.put("cityList",cityList);
        map.put("dataList",dataList);
        return map;
    }
    @RequestMapping("/toLine")
    public String toLine(){
        return "line";
    }
    @RequestMapping("/queryLine")
    @ResponseBody
    public Map<String,List<Object>> queryLineData(){
       //1.近七天查询所有的数据
        List<LineTrend> list7Day=indexService.findSevenData();
        // 2.封装所有的确诊人数
        List<Integer> confirmList=new ArrayList<>();
        //3.封装所有的隔离人数
        List<Integer> isolationList=new ArrayList<>();
        //4.封装所有的治愈人数
        List<Integer> cureList=new ArrayList<>();
        //5.封装所有的死亡人数
        List<Integer> deadList=new ArrayList<>();
        //6.封装所有的疑似人数
        List<Integer> similarList=new ArrayList<>();
        for(LineTrend data:list7Day){
            confirmList.add(data.getConfirm());
            isolationList.add(data.getIsolation());
            cureList.add(data.getCure());
            deadList.add(data.getDead());
            similarList.add(data.getSimilar());
        }
        //7.返回时局的格式容器Map
        Map map=new HashMap<>();
        map.put("confirmList",confirmList);
        map.put("isolationList",isolationList);
        map.put("cureList",cureList);
        map.put("deadList",deadList);
        map.put("similarList",similarList);
        return map;
    }

}
