package com.exia.nocvsystem.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.exia.nocvsystem.entity.User;
import com.exia.nocvsystem.realm.PasswordHelper;
import com.exia.nocvsystem.service.LoginService;
import com.exia.nocvsystem.vo.DataView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
    public DataView login(String cardId,String password,String code,HttpSession session){
        DataView dataView=new DataView();
        if(cardId==null||password==null){
            dataView.setCode(100);
            dataView.setMsg("用户名或密码为空！");
            return dataView;
        }
        //            1.判断验证码是否正确
        String sessionCode = (String) session.getAttribute("code");
        String salt=loginService.FindSalt(cardId);
        password= PasswordHelper.createCredentials(password,salt);
        if (code != null && sessionCode.equals(code)) {
            //2.session普通登录逻辑
//            User user=userService.login(username,password);
            //shiro登录
            try {
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(cardId, password);
                //拿到前台信息
                subject.login(token);
                ////拿到信息
                User user = (User) subject.getPrincipal();
                //3.判断
                if (user != null) {
                    dataView.setCode(200);
                    dataView.setMsg("登陆成功！");
                    session.setAttribute("user", user);
                    return dataView;
                }
            } catch (Exception ex) {
                dataView.setCode(100);
                dataView.setMsg("用户名或密码错误！");
                return dataView;
            }
        }
        dataView.setCode(100);
        dataView.setMsg("验证码错误！");
        return dataView;
    }


    @RequestMapping("/login/loginout")
    //登出
    public String logout(){
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
