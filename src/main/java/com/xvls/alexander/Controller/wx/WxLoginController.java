package com.xvls.alexander.Controller.wx;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.xvls.alexander.entity.wx.UserInfo;
import com.xvls.alexander.entity.wx.WxLoginInfo;
import com.xvls.alexander.entity.wx.WxUserInfo;
import com.xvls.alexander.service.wx.WxUserService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx/auth")
@Validated
public class WxLoginController{

    private static String appid="wx085b04d8a31a7f20";
    private static String secret="2aa9adb26f6a5b91485e8debdff3dfb2";//22c9a164892a1696eef3347fa6c34340

    private final WxMaServiceImpl wxMaService = new WxMaServiceImpl();

    @Autowired
    private WxUserService wxUserService;

    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            return WeChatResponseUtil.badArgument();
        }
        String sessionKey = null;
        String openId = null;

        WxMaInMemoryConfig wxMaInMemoryConfig = new WxMaInMemoryConfig();
        wxMaInMemoryConfig.setAppid(appid);
        wxMaInMemoryConfig.setSecret(secret);
        wxMaService.setWxMaConfig(wxMaInMemoryConfig);
        try {
            WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sessionKey == null || openId == null) {
            return WeChatResponseUtil.fail();
        }
        WxUserInfo wxUserInfo = wxUserService.getWxStudentInfoByOpenId(openId);//通过openId获取用户信息
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("userInfo", wxUserInfo);
        return WeChatResponseUtil.ok(result);
    }

    @RequestMapping("login")
    public Object login(@RequestBody String body){
        String user_num = JacksonUtil.parseString(body,"uid");
        String password = JacksonUtil.parseString(body,"pwd");

        String role = JacksonUtil.parseString(body,"role");//用户角色 教师、学生
        if(role  == null){
            /**默认为学生**/
            role="学生";
        }
        Integer schoolId = JacksonUtil.parseInteger(body,"schoolId");//用户学校

        UserInfo userInfo = JacksonUtil.parseObject(body,"userInfo",UserInfo.class);
        String code = JacksonUtil.parseString(body,"code");
        if (code == null || userInfo == null || user_num == null || password == null || role == null || schoolId == null) {
            return WeChatResponseUtil.badArgument();
        }
        /*    shiro 获取当前的用户    */
        Subject user = SecurityUtils.getSubject();
        /*shiro 封装用户的登录数据*/
        /**根据user_num,school,role获取userId**/
        String userId = wxUserService.getUserId(user_num,schoolId,role).toString();
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
            String sessionKey = null;
            String openId = null;
            WxMaInMemoryConfig wxMaInMemoryConfig = new WxMaInMemoryConfig();
            wxMaInMemoryConfig.setAppid(appid);
            wxMaInMemoryConfig.setSecret(secret);
            wxMaService.setWxMaConfig(wxMaInMemoryConfig);
            try {
                WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(code);
                sessionKey = result.getSessionKey();
                openId = result.getOpenid();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (sessionKey == null || openId == null) {
                return WeChatResponseUtil.fail();
            }
            WxUserInfo wxUserInfo = wxUserService.getWxStudentInfoByUserId(Integer.valueOf(userId));//通过user_num获取用户信息
            wxUserInfo.setOpenid(openId);
            wxUserInfo.setSessionKey(sessionKey);
            wxUserInfo.setNickname(userInfo.getNickName());
            wxUserInfo.setIcon(userInfo.getAvatarUrl());
            wxUserInfo.setWxstatus(1);
            wxUserService.saveWxStudentInfo(wxUserInfo); //绑定用户微信信息
            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("token", token);
            result.put("userInfo", wxUserInfo);
            result.put("wxStatus",wxUserInfo.getWxstatus());
            return WeChatResponseUtil.ok(result);
        }else {
            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("shiroerror",shiroerror);
            return WeChatResponseUtil.fail(999,shiroerror);
        }
    }
}
