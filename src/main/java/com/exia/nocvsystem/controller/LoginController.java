package com.exia.nocvsystem.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.exia.nocvsystem.entity.User;
import com.exia.nocvsystem.service.LoginService;
import com.exia.nocvsystem.vo.DataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;


    @RequestMapping("/toLogin")
    public  String toLogin(){
        return "login";
    }
    //验证码逻辑
    @ResponseBody
    @RequestMapping("/login/getCode")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        //1.验证码对象,HuTool定义图形验证码的长和宽，验证码的位数，干扰线的条数
        LineCaptcha captcha=CaptchaUtil.createLineCaptcha(116,36,4,10);
        //2.放到session
        session.setAttribute("code",captcha.getCode());
        //3.输出
        ServletOutputStream outputStream= response.getOutputStream();
        captcha.write(outputStream);
        //4.关闭输出流
        outputStream.close();
    }
    //登录
    @RequestMapping("/login/login")
    @ResponseBody
    public DataView login(String username,String password,String code,HttpSession session){
        DataView dataView=new DataView();
        //1.首先判断验证码对不对
        String sessionCode=(String)session.getAttribute("code");
        if(code!=null && sessionCode.equals(code)){
            //2.登录逻辑
            User user=loginService.login(username,password);
            if(user!=null){
                dataView.setCode(200);
                dataView.setMsg("登录成功！");
                //放入session
                session.setAttribute("user",user);
                return dataView;
            }else{
                dataView.setCode(100);
                dataView.setMsg("用户名或者密码错误，登录失败！");

            }
        }
        dataView.setCode(100);
        dataView.setMsg("验证码错误，登录失败！");
        return dataView;

    }
}
