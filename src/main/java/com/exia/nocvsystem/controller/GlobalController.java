package com.exia.nocvsystem.controller;

import com.exia.nocvsystem.entity.NocvGlobalData;
import com.exia.nocvsystem.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GlobalController {
    @Autowired
    private GlobalService globalService;
    @RequestMapping("/toGlobal")
    public String toGlobal(){
        return "Global";
    }
    @RequestMapping("/queryGlobal")
    @ResponseBody
    public List<NocvGlobalData> queryGlobalData(){
        List<NocvGlobalData> list=globalService.list();
        return list;
    }
}
