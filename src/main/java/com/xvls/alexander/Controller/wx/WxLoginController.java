package com.xvls.alexander.Controller.wx;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.UserInfo;
import com.xvls.alexander.entity.wx.WxLoginInfo;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.entity.wx.WxUser;
import com.xvls.alexander.service.wx.UsersService;
import com.xvls.alexander.service.wx.WxUserService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx/auth")
@Validated
// TODO: 2020/2/11 完成登录上对WxUser和Users的转换，之后需要去评论功能进行转换
public class WxLoginController{

    private static String appid="wx085b04d8a31a7f20";
    private static String secret="2aa9adb26f6a5b91485e8debdff3dfb2";//22c9a164892a1696eef3347fa6c34340

    private final WxMaServiceImpl wxMaService = new WxMaServiceImpl();

    @Autowired
    private UsersService usersService;
    @Autowired
    private WxUserService wxUserService;

    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null ) {
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
        WxUser wxUser = wxUserService.getWxUserInfoByOpenId(openId);//通过openId获取用户信息
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("wxUser", wxUser);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 进行授权，即对用户的个人信息进行更新
     * @param body
     * @return
     */
    @RequestMapping("updateLoginInfo")
    public Object updateLoginInfo(@RequestBody String body){
        if(body==null){
            return WeChatResponseUtil.badArgument();
        }
        UserInfo userInfo = null;
        Integer wxUserId = null;
        try{
            userInfo = JacksonUtil.parseObject(body,"userInfo",UserInfo.class);
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
        }catch (Exception e){
            System.out.println(e.getCause());
            return WeChatResponseUtil.badArgument();
        }
        if(wxUserId==null || userInfo==null||
                (userInfo.getAvatarUrl()==null && userInfo.getCity()==null && userInfo.getAvatarUrl()==null && userInfo.getCountry()==null && userInfo.getGender()==null
                        && userInfo.getLanguage()==null && userInfo.getNickname()==null && userInfo.getProvince()==null)){
            return WeChatResponseUtil.badArgumentValue();
        }
        /**更新数据**/
        wxUserService.updateLoginInfo(wxUserId,userInfo);
        return WeChatResponseUtil.ok();
    }

    /**
     * 登录教务系统绑定学号，将userId对应的用户绑定微信信息；
     * @param body
     * @return
     */
    @RequestMapping("login")
    public Object login(@RequestBody String body){
        String userNum = JacksonUtil.parseString(body,"userId");
        String password = JacksonUtil.parseString(body,"password");

        String role = JacksonUtil.parseString(body,"role");//用户角色 教师、学生
        if(role  == null){
            /**默认为学生**/
            role="学生";
        }
        Integer schoolId = JacksonUtil.parseInteger(body,"schoolId");//用户学校

        Integer wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
        if (wxUserId == null  || userNum == null || password == null || role == null || schoolId == null) {
            return WeChatResponseUtil.badArgument();
        }
        /*    shiro 获取当前的用户    */
        Subject user = SecurityUtils.getSubject();
        /*shiro 封装用户的登录数据*/
        /**根据user_num,school,role获取userId**/
        Integer userId_temp = usersService.getUserId(userNum,schoolId,role);

        if(userId_temp==null){
            return WeChatResponseUtil.fail(401,"账号或密码错误！");
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
            /**绑定微信信息**/
            usersService.updateUsersWxUserIdByUserId(userId_temp,wxUserId);

            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("wxUserId",wxUserId);
            result.put("userId", userId);
            result.put("userNum",userNum);
            //result.put("wxStatus", users.getWxstatus());
            return WeChatResponseUtil.ok(result);
        }else {
            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("shiroerror",shiroerror);
            return WeChatResponseUtil.fail(999,shiroerror);
        }
    }


    /**
     * 一开始进行登录，需要发送code，再通过openId查找是否有该用户
     * 1.没有就是新用户，插入到 wx_user 表中，返回 wxUserId、userId=null，进行下一步操作；
     * 2.有的话就是老用户，将 wxUserId 返回，对于绑定了学号的用户，将wxUserId，userId返回。
     * @return
     */
    @RequestMapping("first")
    public Object loginFirst(@RequestParam("code") String code){//@RequestBody String body
        //String code = JacksonUtil.parseString(body,"code");
        if(code==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        /**通过code获取openId和sessionKey**/
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
            System.out.println(e.getCause());
            return WeChatResponseUtil.fail();
        }
        if (sessionKey == null || openId == null) {
            return WeChatResponseUtil.fail();
        }
        /**通过openId查询微信用户**/
        Integer userId = null;
        Map map = Maps.newHashMap();
        WxUser wxUser = wxUserService.getWxUserInfoByOpenId(openId);
        Integer wxUserId = null;
        if(wxUser ==null){
            /**为新用户，没有任何记录，插入微信表中**/
            WxUser wxUser1 = new WxUser();
            wxUser1.setOpenid(openId);
            wxUser1.setSessionKey(sessionKey);
            wxUserId = wxUserService.insertNewWxUser(wxUser1);
            if(wxUserId == null){
                return WeChatResponseUtil.updatedDataFailed();
            }
        }else {
            wxUserId = wxUser.getWxUserId();
        }
        /**判断是否已经绑定学号**/
        Users users = usersService.getUsersByWxUserId(wxUserId);

        if(users==null || users.getUserNum()==null || users.getPassword() == null){
            map.put("wxUserId",wxUserId);
            map.put("userId",userId);
            map.put("userNum",null);
            return WeChatResponseUtil.ok(map);
        }else{
            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("userId", users.getUserId());
            result.put("userNum",users.getUserNum());
            result.put("wxUserId", wxUserId);
            result.put("wxStatus", wxUser.getWxstatus());
            return WeChatResponseUtil.ok(result);
        }
    }

    /**
     * 对于新用户，不对学号进行绑定，直接插入新用户，返回userId
     * @return
     */
    /*@RequestMapping("loginNoBindUserNum")
    public Object loginNoBindUserNum(@RequestParam("code") String code){
        if(code==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        *//**通过code获取openId和sessionKey**//*
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
            System.out.println(e.getCause());
            return WeChatResponseUtil.fail();
        }
        if (sessionKey == null || openId == null) {
            return WeChatResponseUtil.fail();
        }
        *//**插入新用户，没有学号等信息，有openId和sessionKey**//*
        Users users = new Users();
        users.setOpenid(openId);
        users.setSessionKey(sessionKey);
        users.setWxstatus(1);
        Integer userId = usersService.insertNewWxUser(users);
        Map map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("userNum",null);

        return WeChatResponseUtil.ok(map);
    }*/

}
