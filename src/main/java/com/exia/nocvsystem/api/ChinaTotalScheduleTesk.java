package com.exia.nocvsystem.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exia.nocvsystem.entity.ChinaTotal;
import com.exia.nocvsystem.entity.NocvData;
import com.exia.nocvsystem.entity.NocvGlobalData;
import com.exia.nocvsystem.service.ChinaTotalService;
import com.exia.nocvsystem.service.ChinaTotalServiceImpl;
import com.exia.nocvsystem.service.GlobalService;
import com.exia.nocvsystem.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class ChinaTotalScheduleTesk {
    //每小时执行一次
    @Autowired
    private ChinaTotalService chinaTotalService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private GlobalService globalService;
    @Scheduled(fixedDelay = 100000)
    public void updateChinaTotalToDB() throws Exception {
        HttpUtils httpUtils=new HttpUtils();
        String str=httpUtils.getData();
        //System.out.println("原始数据"+str);
        //1.所有数据的alibaba格式
        JSONObject jsonObject=JSONObject.parseObject(str);
        Object data=jsonObject.get("data");
        //System.out.println("data:"+data);
        //2.拿到data
        JSONObject jsonData=JSONObject.parseObject(data.toString());
        Object confirm=jsonData.get("econNum");
        System.out.println("现存确诊总人数:"+confirm);
        Object updateTime=jsonData.get("mtime");
        System.out.println("统计数据截至日期:"+updateTime);
        Object input=jsonData.get("jwsrNum");
        System.out.println("境外输入:"+input);
        Object dead=jsonData.get("deathtotal");
        System.out.println("死亡总人数:"+dead);
        Object severe=jsonData.get("asymptomNum");
        System.out.println("现存重症:"+severe);
        Object heal=jsonData.get("curetotal");
        System.out.println("治愈人数:"+heal);
        //为程序实体赋值
        ChinaTotal dataEntity=new ChinaTotal();
        dataEntity.setConfirm(Integer.valueOf(confirm.toString()));
        dataEntity.setInput(Integer.valueOf(input.toString()));
        dataEntity.setDead(Integer.valueOf(dead.toString()));
        dataEntity.setSevere(Integer.valueOf(severe.toString()));
        dataEntity.setHeal(Integer.valueOf(heal.toString()));
        dataEntity.setSuspect(0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date update_time = format.parse(updateTime.toString());
        dataEntity.setUpdateTime(update_time);
        //6.插入数据库(更新)
        chinaTotalService.save(dataEntity);
        //拿到areaTree
        JSONArray areaTree=jsonData.getJSONArray("worldlist");
        Object[] objects=areaTree.toArray();
        List<NocvGlobalData> nocvGlobalDataList=new ArrayList<>();
        //遍历所有国家的数据
        for(int i=0;i<objects.length;i++){
            NocvGlobalData nocvGlobalData=new NocvGlobalData();
            JSONObject jsonObject1=JSONObject.parseObject(objects[i].toString());
            Object name=jsonObject1.get("name");
            Object value=jsonObject1.get("value");
            nocvGlobalData.setName(name.toString());
            nocvGlobalData.setValue(Integer.parseInt(value.toString()));
            nocvGlobalDataList.add(nocvGlobalData);
            System.out.println(name.toString()+":"+value);

        }
        globalService.saveBatch(nocvGlobalDataList);
        //拿到中国的数据
        //JSONObject jsonObject1=JSONObject.parseObject(objects[1].toString());
        JSONArray children=jsonData.getJSONArray("list");
        Object[] objects1=children.toArray();

        List<NocvData> nocvDataList=new ArrayList<>();
        for (int i=0;i<objects1.length;i++){
//            NocvData nocvData=new NocvData();
//            JSONObject jsonObject2=JSONObject.parseObject(objects1[i].toString());
//            Object name=jsonObject2.get("name");//省份名字
//            JSONObject jsonObject3=JSONObject.parseObject(objects1[i].toString());
//            Object value=jsonObject3.get("value");//确诊数量
////            JSONObject jsonObject4=JSONObject.parseObject(objects1[i].toString());
////            Object name4=jsonObject4.get("name");//省份名字
////            JSONObject jsonObject5=JSONObject.parseObject(objects1[i].toString());
////            Object name5=jsonObject5.get("name");//省份名字
//            System.out.println("省份->"+name+":"+value+"人");
//            nocvData.setName(name.toString());
//            nocvData.setValue(Integer.parseInt(value.toString()));
//            if(update_time==null){
//                nocvData.setUpdateTime(new Date());
//            }else
//                nocvData.setUpdateTime(update_time);
//            nocvDataList.add(nocvData);
        }
        //各个省份的数据插入数据库
        indexService.saveBatch(nocvDataList);
    }
}
