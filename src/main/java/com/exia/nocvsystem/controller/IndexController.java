package com.exia.nocvsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.exia.nocvsystem.entity.ChinaTotal;
import com.exia.nocvsystem.entity.LineTrend;
import com.exia.nocvsystem.entity.NocvData;
import com.exia.nocvsystem.entity.NocvNews;
import com.exia.nocvsystem.service.ChinaTotalService;
import com.exia.nocvsystem.service.IndexService;
import com.exia.nocvsystem.service.NocvNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.util.*;

@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private ChinaTotalService chinaTotalService;
    @Autowired
    private NocvNewsService nocvNewsService;
    //查询chinaTotal数据
    @RequestMapping("/")
    public String index(Model model) throws ParseException {
        //1.找到ID最大的数据
        Integer id=chinaTotalService.maxID();
        //2.根据ID查找数据
        ChinaTotal chinaTotal=chinaTotalService.getById(id);
        model.addAttribute("chinaTotal",chinaTotal);

        return "index";
    }
    @RequestMapping("/toChina")
    public String toChina(Model model) throws ParseException {
        //1.找到ID最大的数据
        Integer id=chinaTotalService.maxID();
        //2.根据ID查找数据
        Jedis jedis = new Jedis("127.0.0.1");
        //redis查询数据库逻辑
        /**
         * 1.先查询缓存，有数据直接返回，没有数据查询mysql数据库，更新缓存，返回客户端
         */
        if (jedis!=null) {
            String confirm = jedis.get("confirm");
            String input = jedis.get("input");
            String heal = jedis.get("heal");
            String dead = jedis.get("dead");
            String updateTime = jedis.get("updateTime");
            if (StringUtils.isNotBlank(confirm)
                    && StringUtils.isNotBlank(input)
                    && StringUtils.isNotBlank(heal)
                    && StringUtils.isNotBlank(dead)
                    && StringUtils.isNotBlank(updateTime)) {
                ChinaTotal chinaTotalRedis = new ChinaTotal();
                chinaTotalRedis.setConfirm(Integer.parseInt(confirm));
                chinaTotalRedis.setInput(Integer.parseInt(input));
                chinaTotalRedis.setHeal(Integer.parseInt(heal));
                chinaTotalRedis.setDead(Integer.parseInt(dead));
                // 格式调整 String ----> Date
                chinaTotalRedis.setUpdateTime(new Date());
                System.out.println("redis中的数据：" + chinaTotalRedis);
                // 扔回前台
                model.addAttribute("chinaTotal", chinaTotalRedis);
                // 3.疫情播报新闻
                List<NocvNews> newsList = nocvNewsService.listNewsLimit5();
                model.addAttribute("newsList", newsList);
                return "china";
            } else {
                // 2.3 缓存里面没有数据 查询数据
                ChinaTotal chinaTotal = chinaTotalService.getById(id);
                model.addAttribute("chinaTotal", chinaTotal);
                // 3.疫情播报新闻
                List<NocvNews> newsList = nocvNewsService.listNewsLimit5();
                model.addAttribute("newsList", newsList);
                // 2.4 更新缓存
                jedis.set("confirm", String.valueOf(chinaTotal.getConfirm()));
                jedis.set("input", String.valueOf(chinaTotal.getInput()));
                jedis.set("heal", String.valueOf(chinaTotal.getHeal()));
                jedis.set("dead", String.valueOf(chinaTotal.getDead()));
                jedis.set("updateTime", String.valueOf(chinaTotal.getUpdateTime()));

                return "china";
            }
        }
        ChinaTotal chinaTotal=chinaTotalService.getById(id);
        model.addAttribute("chinaTotal",chinaTotal);
        List<NocvNews> newsList=nocvNewsService.listNewsLimit5();
        model.addAttribute("newsList",newsList);
        return "china";
    }
    @RequestMapping("/query")
    @ResponseBody
    public List<NocvData> queryData() throws ParseException {
        // 1.先查redis缓存[List] 有数据返回即可
        Jedis jedis = new Jedis("127.0.0.1");
        if (jedis!=null){

            // 1.1 有缓存数据 返回数据即可
            List<String> listRedis = jedis.lrange("nocvdata", 0 ,33);
            List<NocvData> dataList = new ArrayList<>();
            if (listRedis.size()>0){
                for(int i=0; i<listRedis.size(); i++) {
                    System.out.println("列表项为: "+listRedis.get(i));
                    String s = listRedis.get(i);
                    JSONObject jsonObject = JSONObject.parseObject(s);
                    Object name = jsonObject.get("name");
                    Object value = jsonObject.get("value");
                    NocvData nocvData = new NocvData();
                    nocvData.setName(String.valueOf(name));
                    nocvData.setValue(Integer.parseInt(value.toString()));
                    dataList.add(nocvData);
                }
                // 查询redis缓存数据库 返回的数据
                return dataList;
            }else{
                // 1.2 redis没有数据 查Mysql数据库,更新缓存
                List<NocvData> list = indexService.listOrOrderByLimit34();
                for (NocvData nocvData : list){
                    jedis.lpush("nocvdata", JSONObject.toJSONString(nocvData));
                }
                // 返回的数据中的数据
                return list;
            }
        }
        // 默认没有连接redis的返回数据库【兼容有没有安装redis】
        List<NocvData> list = indexService.listOrOrderByLimit34();
        return list;
    }
    //跳转pie页面
    @RequestMapping("/toPie")
    public String toPie(){
        return "pie";
    }
    /**
     * 分组聚合
     *SQL: select count(*) form foods group by tyoe
     * **/
    @RequestMapping("/queryPie")
    @ResponseBody
    public List<NocvData> queryPieData(){
        List<NocvData> list=indexService.listOrOrderByLimit34();
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
        List<NocvData> list=indexService.listOrOrderByLimit34();
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
