package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.service.UsersService;
import com.xvls.alexander.service.wx.WxUsersService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/users")
public class UsersController {

    @Autowired
    UsersService usersService;
    @Autowired
    WxUsersService wxUsersService;

    /**
     * 通过 userId 获得个人信息
     * @param userId
     * @return
     */
    @RequestMapping("getWxUsersInfoById")
    public Object getWxUsersInfoById(@RequestParam("userId") Integer userId){
        if(userId == null){
            return WeChatResponseUtil.badArgument();
        }
        Users wxUsersInfo = usersService.getWxUsersInfo(userId);
        Map result = Maps.newHashMap();
        result.put("wxUsersInfo",wxUsersInfo);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 微信端 修改教务系统账号的密码
     * @return
     */
    @RequestMapping("wxUpdatePassword")
    public Object wxUpdatePassword(@RequestParam("userId") Integer userId,@RequestParam("oldPassword")String oldPassword,@RequestParam("password")String password){
        if(userId == null || oldPassword == null || password == null){
            return WeChatResponseUtil.badArgument();
        }
        Users users = wxUsersService.getUserInfoByUserId(userId);
        if(users==null){
            return WeChatResponseUtil.fail(-1,"对不起，请重新登录");
        }
        String password_temp = usersService.generatePassword(oldPassword, users.getSalt());
        if(!password_temp.equals(users.getPassword())){
            return WeChatResponseUtil.fail(-1,"密码错误！");
        } else{
            usersService.wxUpdatePassword(userId,password);
            return WeChatResponseUtil.ok();
        }

    }

    /**
     * 微信端 修改个人信息
     * @return
     */
    @RequestMapping("wxUpdatePersonalInfo")
    public Object wxUpdatePersonalInfo(@RequestParam("userId")Integer userId,
                                       @RequestParam("phone")String phone,
                                       @RequestParam("mail")String mail,
                                       @RequestParam("motto")String motto){
        if(userId == null || phone == null || mail == null || motto == null){
            return WeChatResponseUtil.badArgument();
        }
        usersService.wxUpdatePersonalInfo(userId,phone,mail,motto);
        return WeChatResponseUtil.ok();
    }

    /**
     * 微信端 修改 phone
     * @param userId
     * @param phone
     * @return
     */
    @RequestMapping("wxUpdatePhone")
    public Object wxUpdatePhone(@RequestParam("userId") Integer userId,@RequestParam("phone")String phone){
        if(userId == null || phone == null){
            return WeChatResponseUtil.badArgument();
        }
        usersService.wxUpdatePhone(userId,phone);
        return WeChatResponseUtil.ok();
    }

    /**
     * 微信端 修改mail
     * @param userId
     * @param mail
     * @return
     */
    @RequestMapping("wxUpdateMail")
    public Object wxUpdateMail(@RequestParam("userId") Integer userId,@RequestParam("mail")String mail){
        if(userId == null || mail == null){
            return WeChatResponseUtil.badArgument();
        }
        usersService.wxUpdateMail(userId,mail);
        return WeChatResponseUtil.ok();
    }

    /**
     * 微信端 修改motto
     * @param userId
     * @param motto
     * @return
     */
    @RequestMapping("wxUpdateMotto")
    public Object wxUpdateMotto(@RequestParam("userId")Integer userId,@RequestParam("motto")String motto){
        if(userId == null || motto == null){
            return WeChatResponseUtil.badArgument();
        }
        usersService.wxUpdateMotto(userId,motto);
        return WeChatResponseUtil.ok();
    }
}
