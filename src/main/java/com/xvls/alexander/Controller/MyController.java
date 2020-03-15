package com.xvls.alexander.Controller;


import com.xvls.alexander.utils.RestResponse;
import com.xvls.alexander.utils.SystemResponse;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
public class MyController {

    @CrossOrigin
    @RequestMapping({"/","/index"})
    @ResponseBody
    public String toIndex(Model model){
        //model.addAttribute("msg","hello,shiro");
        return "index";
    }

    @CrossOrigin
    @RequestMapping("/user/add")
    @ResponseBody
    public String add(){
        return "/user/add";
    }

    @CrossOrigin
    @RequestMapping("/user/update")
    @ResponseBody
    public String update(){
        return "/user/update";
    }

    @CrossOrigin
    @RequestMapping("/toLogin")
    @ResponseBody
    public Object toLogin(){
        return SystemResponse.unlogin();//请登录
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

    @CrossOrigin
    @RequestMapping("/unauthorized")
    @ResponseBody
    public Object unauthorized(){
        return SystemResponse.unauthz();//未经授权
    }
}
