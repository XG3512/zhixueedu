package com.xvls.alexander.Controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @RequestMapping({"/","/index"})
    @ResponseBody
    public String toIndex(Model model){
        //model.addAttribute("msg","hello,shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    @ResponseBody
    public String add(){
        return "/user/add";
    }

    @RequestMapping("/user/update")
    @ResponseBody
    public String update(){
        return "/user/update";
    }

    @RequestMapping("/toLogin")
    @ResponseBody
    public String toLogin(){
        return "login";
    }

    /*@RequestMapping("/login")
    @ResponseBody
    public String login(String username,String password){
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try {
            subject.login(token);//执行登录方法，如果没有异常就登录成功
        } catch (UnknownAccountException e) {
            System.out.println("用户名不存在");
            e.printStackTrace();
            return "UnknownAccountException";
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误");
            e.printStackTrace();
            return "IncorrectCredentialsException";
        }

        return "success";
    }*/

    @RequestMapping("/unauthorized")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }
}
