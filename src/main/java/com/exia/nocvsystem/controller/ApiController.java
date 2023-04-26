package com.exia.nocvsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/25 10:42
 */
@Controller
public class ApiController {
    @RequestMapping("/toChinaManager")
    public String toChinaManager(){
        return "admin/chinadatamanager";
    }
}
