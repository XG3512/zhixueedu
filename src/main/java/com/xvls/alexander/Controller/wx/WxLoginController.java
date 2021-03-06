package com.xvls.alexander.Controller.wx;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.UserInfo;
import com.xvls.alexander.entity.wx.WxLoginInfo;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.entity.wx.WxUser;
import com.xvls.alexander.service.wx.WxUsersService;
import com.xvls.alexander.service.wx.WxUserService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx/auth")
@Validated
public class WxLoginController{

    //private static String appid="wxfc75ed61bcc8838e";
    //private static String secret="78b473fdb261fa9b25ba9e8fd7d32d13";//22c9a164892a1696eef3347fa6c34340
    @Value("${xvls_weixin.appid}")
    private String appid;
    @Value("${xvls_weixin.secret}")
    private String secret;

    private final WxMaServiceImpl wxMaService = new WxMaServiceImpl();

    @Autowired
    private WxUsersService wxUsersService;
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
        WxUser wxUser = wxUserService.getWxUserInfoByOpenId(openId);//??????openId??????????????????
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("wxUser", wxUser);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * ??????????????????????????????????????????????????????
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
        if(wxUserId==null || userInfo==null || userInfo.getNickname()==null || userInfo.getAvatarUrl() == null){
            return WeChatResponseUtil.badArgumentValue();
        }
        /**????????????**/
        wxUserService.updateLoginInfo(wxUserId,userInfo);
        return WeChatResponseUtil.ok();
    }

    /**
     * ????????????????????????????????????userId????????????????????????????????????
     * @param body
     * @return
     */
    @RequestMapping("login")
    public Object login(@RequestBody String body, HttpServletRequest request, HttpServletResponse response){
        String userNum = JacksonUtil.parseString(body,"userId");
        String password = JacksonUtil.parseString(body,"password");

        String role = JacksonUtil.parseString(body,"role");//???????????? ???????????????
        if(role  == null){
            /**???????????????**/
            role="??????";
        }
        Integer schoolId = JacksonUtil.parseInteger(body,"schoolId");//????????????

        Integer wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
        if (wxUserId == null  || userNum == null || password == null || role == null || schoolId == null) {
            return WeChatResponseUtil.badArgument();
        }
        /*    shiro ?????????????????????    */
        Subject user = SecurityUtils.getSubject();
        /*shiro ???????????????????????????*/
        /**??????user_num,school,role??????userId**/
        Integer userId_temp = wxUsersService.getUserId(userNum,schoolId,role);

        if(userId_temp==null){
            return WeChatResponseUtil.fail(401,"????????????????????????");
        }
        String userId = userId_temp.toString();
        UsernamePasswordToken token = new UsernamePasswordToken(userId,password);
        String shiroerror = null;
        try {
            user.login(token);//??????????????????????????????????????????????????????
        }catch (IncorrectCredentialsException e) {
            shiroerror = "??????????????????.";
        } catch (ExcessiveAttemptsException e) {
            shiroerror = "????????????????????????";
        } catch (LockedAccountException e) {
            shiroerror = "??????????????????.";
        } catch (DisabledAccountException e) {
            shiroerror = "??????????????????.";
        } catch (ExpiredCredentialsException e) {
            shiroerror = "???????????????.";
        } catch (UnknownAccountException e) {
            shiroerror = "???????????????";
        } catch (UnauthorizedException e) {
            shiroerror = "?????????????????????????????????";
        }
        if (shiroerror == null){
            /**??????????????????**/
            wxUsersService.updateUsersWxUserIdByUserId(userId_temp,wxUserId);

            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("wxUserId",wxUserId);
            result.put("userId", userId);
            result.put("userNum",userNum);
            result.put("token",user.getSession().getId());
            System.out.println("login session:"+user.getSession().getId());
            //result.put("wxStatus", users.getWxstatus());
            return WeChatResponseUtil.ok(result);
        }else {
            Map<Object, Object> result = new HashMap<Object, Object>();
            result.put("shiroerror",shiroerror);
            return WeChatResponseUtil.fail(999,shiroerror);
        }
    }


    /**
     * ????????????????????????????????????code????????????openId????????????????????????
     * 1.????????????????????????????????? wx_user ??????????????? wxUserId???userId=null???????????????????????????
     * 2.?????????????????????????????? wxUserId ?????????????????????????????????????????????wxUserId???userId?????????
     * @return
     */
    @RequestMapping("first")
    public Object loginFirst(@RequestParam("code") String code){//@RequestBody String body
        //String code = JacksonUtil.parseString(body,"code");
        if(code==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        /**??????code??????openId???sessionKey**/
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
        /**??????openId??????????????????**/
        Integer userId = null;
        Map map = Maps.newHashMap();
        WxUser wxUser = wxUserService.getWxUserInfoByOpenId(openId);
        Integer wxUserId = null;
        if(wxUser ==null){
            /**??????????????????????????????????????????????????????**/
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
        /**??????????????????????????????**/
        Users users = wxUsersService.getUsersByWxUserId(wxUserId);

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
     * ????????????????????????????????????
     * @param wxUserId
     * @return
     */
    @RequestMapping("wxUserLogout")
    public Object wxUserLogout(@RequestParam("wxUserId") Integer wxUserId){
        if(wxUserId == null){
            return WeChatResponseUtil.badArgument();
        }
        Users users = wxUsersService.getUsersByWxUserId(wxUserId);
        if(users==null){
            return WeChatResponseUtil.fail(-1,"????????????????????????????????????");
        }
        wxUserService.wxUserLogout(users.getUserId());
        return WeChatResponseUtil.ok();
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????userId
     * @return
     */
    /*@RequestMapping("loginNoBindUserNum")
    public Object loginNoBindUserNum(@RequestParam("code") String code){
        if(code==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        *//**??????code??????openId???sessionKey**//*
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
        *//**?????????????????????????????????????????????openId???sessionKey**//*
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
