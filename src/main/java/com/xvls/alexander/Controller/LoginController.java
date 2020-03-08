package com.xvls.alexander.Controller;

import com.xvls.alexander.entity.System_menu;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.service.System_menuService;
import com.xvls.alexander.service.wx.UsersService;
import com.xvls.alexander.utils.Constants;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/login")
public class LoginController {

    @Autowired
    UsersService usersService;
    @Autowired
    System_menuService system_menuService;

    /**
     * 后台管理端 登录 功能，通过 userNum,password,schoolId,role
     * @param body
     * @return
     */
    @RequestMapping("login")
    public Object login(@RequestBody String body, HttpServletRequest request){

        String userNum = null;
        String password = null;
        String role = null;//用户角色 教师、学生
        Integer schoolId = null;//用户学校
        Boolean rememberMe = null;//记住我
        String code = null;//验证码
        try {
            userNum = JacksonUtil.parseString(body,"userNum");
            password = JacksonUtil.parseString(body,"password");
            schoolId = JacksonUtil.parseInteger(body,"schoolId");
            role = JacksonUtil.parseString(body,"role");
            rememberMe = JacksonUtil.parseBoolean(body,"rememberMe");
            code = JacksonUtil.parseString(body,"code");

            if (userNum == null || password == null || role == null || schoolId == null || rememberMe == null || code==null) {
                return SystemResponse.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.badArgument();
        }
        HttpSession session = request.getSession();
        if(session == null){
            return SystemResponse.fail(-1,"session超时");
        }
        String trueCode = (String)session.getAttribute(Constants.VALIDATE_CODE);
        if(StringUtils.isBlank(trueCode)){
            return SystemResponse.fail(-1,"验证码超时");
        }
        if(StringUtils.isBlank(code) || !trueCode.toLowerCase().equals(code.toLowerCase())){
            return SystemResponse.fail(-1,"验证码错误");
        }
        /*    shiro 获取当前的用户    */
        Subject user = SecurityUtils.getSubject();
        /*shiro 封装用户的登录数据*/
        /**根据user_num,school,role获取userId**/
        // TODO: 2020/3/8 有错误，需要在mapper中使用嵌套查询
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
        UsernamePasswordToken token = new UsernamePasswordToken(userId,password,rememberMe);
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
            List<System_menu> menuList = system_menuService.getMenuListById(userInfo.getUserId());

            result.put("userInfo",userInfo);
            result.put("menuList",menuList);
            result.put("token",token);

            return SystemResponse.ok(result);
        }else {
            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("shiroerror",shiroerror);
            return SystemResponse.fail(999,shiroerror);
        }
    }

    /**
     * 通过token登录
     * @return
     */
    @RequestMapping("loginByToken")
    public Object loginByToken(@RequestBody String body){
        UsernamePasswordToken token = null;
        try {
            token = JacksonUtil.parseObject(body,UsernamePasswordToken.class);
            if(token == null || token.getUsername() == null || token.getPassword()==null){
                return SystemResponse.badArgument();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer userId = Integer.valueOf(token.getUsername());
        /*    shiro 获取当前的用户    */
        Subject user = SecurityUtils.getSubject();
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
            List<System_menu> menuList = system_menuService.getMenuListById(userId);
            Users userInfo = usersService.getUserInfoByUserId(userId);
            result.put("userInfo",userInfo);
            result.put("menuList",menuList);
            result.put("token",token);

            return SystemResponse.ok(result);
        }else {
            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("shiroerror",shiroerror);
            return SystemResponse.fail(999,shiroerror);
        }
    }

    /**
     * 退出系统
     * @return
     */
    @RequestMapping("logout")
    public Object logout(){
        SecurityUtils.getSubject().logout();
        return SystemResponse.ok();
    }
}
