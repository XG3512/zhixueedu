package com.xvls.alexander.Controller;

import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.service.wx.UsersService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/system/login")
public class LoginController {

    @Autowired
    UsersService usersService;

    /**
     * 后台管理端 登录 功能，通过 userNum,password,schoolId,role
     * @param body
     * @return
     */
    @RequestMapping("login")
    public Object login(@RequestBody String body){

        String userNum = null;
        String password = null;
        String role = null;//用户角色 教师、学生
        Integer schoolId = null;//用户学校
        try {
            userNum = JacksonUtil.parseString(body,"userNum");
            password = JacksonUtil.parseString(body,"password");
            schoolId = JacksonUtil.parseInteger(body,"schoolId");
            role = JacksonUtil.parseString(body,"role");
            if (userNum == null || password == null || role == null || schoolId == null) {
                return SystemResponse.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.badArgument();
        }

        /*    shiro 获取当前的用户    */
        Subject user = SecurityUtils.getSubject();
        /*shiro 封装用户的登录数据*/
        /**根据user_num,school,role获取userId**/
        Users userInfo = usersService.getUserInfo(userNum, schoolId, role);
        if(userInfo == null){
            return SystemResponse.fail(-1,"账号或密码错误！");
        }
        Integer userId_temp = userInfo.getUserId();
        System.out.println(userId_temp);
        if(userId_temp==null){
            return SystemResponse.fail(-1,"账号或密码错误！");
        }
        String userId = userId_temp.toString();
        UsernamePasswordToken token = new UsernamePasswordToken(userId,password);
        String shiroerror = null;
        try {
            user.login(token);//执行登录方法，如果没有异常就登录成功
        }catch (IncorrectCredentialsException e) {
            shiroerror = "登录密码错误.";
        } catch (ExcessiveAttemptsException e) {
            shiroerror = "登录失败次数过多";
        } catch (LockedAccountException e) {
            shiroerror = "帐号已被锁定.";
        } catch (DisabledAccountException e) {
            shiroerror = "帐号已被禁用.";
        } catch (ExpiredCredentialsException e) {
            shiroerror = "帐号已过期.";
        } catch (UnknownAccountException e) {
            shiroerror = "帐号不存在";
        } catch (UnauthorizedException e) {
            shiroerror = "您没有得到相应的授权！";
        }
        if (shiroerror == null){

            Map<Object, Object> result = new HashMap<Object, Object>();
            /*result.put("userId", userId);
            result.put("userNum",userNum);
            result.put("schoolId",schoolId);
            result.put("roleList",userInfo.getRoleList());
            result.put("permissionList",userInfo.getPermissions());*/
            result.put("userInfo",userInfo);
            return WeChatResponseUtil.ok(result);
        }else {
            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("shiroerror",shiroerror);
            return SystemResponse.fail(999,shiroerror);
        }
    }
}
