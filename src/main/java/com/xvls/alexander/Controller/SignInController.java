package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.*;
import com.xvls.alexander.service.SignInService;
import com.xvls.alexander.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 签到
 */
@CrossOrigin
@RestController
@RequestMapping("/SignIn")
public class SignInController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignInController.class);


    @Autowired
    SignInService signInService;

    /**
     * 第一次，创建签到信息 + 生成二维码
     * @param body
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequiresPermissions("signIn_info:add")
    @RequestMapping("FirstGetSignInImage")
    public Object FirstGetSignInImage(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SignIn_info signIn_info = null;
        try {
            signIn_info = JacksonUtil.parseObject(body,SignIn_info.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return SystemResponse.fail(-1,"json转换出错");
        }
        if(signIn_info==null || signIn_info.getStartTime()==null || signIn_info.getEndTime()==null || signIn_info.getTcId()==null || signIn_info.getTeacherId()==null){
            return SystemResponse.badArgument();
        }
        String url = null;
        Integer siiId = null;
        BufferedImage image = null;
        try {
            siiId = signInService.insertSignInfo(signIn_info);
            image = signInService.GetSignInImage(siiId, signIn_info.getStartTime(), signIn_info.getEndTime());
        } catch (Exception e) {
            e.printStackTrace();
            return SystemResponse.fail(-1,e.getMessage());
        }
        /*Map result = Maps.newHashMap();
        result.put("url",url);
        result.put("startTime",signIn_info.getStartTime());
        result.put("endTime",signIn_info.getEndTime());*/

        /**以流的形式输出到前端*/
        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //将验证码放到HttpSession里面
        LOGGER.info("课程"+siiId+"的签到二维码已经生成");
        //设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");

        ImageIO.write(image, "JPEG", response.getOutputStream());
        return null;
    }

    /**
     * 已经有的签到信息，重新获得签到二维码
     * @param body
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequiresPermissions("signIn_info:add")//只要能添加，就能获取签到码
    @RequestMapping("ReGetSignInImage")
    public Object ReGetSignInImage(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer siiId = null;
        try {
            siiId = JacksonUtil.parseInteger(body,"siiId");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return SystemResponse.fail(-1,"json转换出错");
        }
        if(siiId == null){
            return SystemResponse.badArgument();
        }
        SignIn_info signIn_info = signInService.getSignIn_infoBySiiId(siiId);
        if(signIn_info==null || signIn_info.getSiiId()==null || signIn_info.getStartTime()==null || signIn_info.getEndTime()==null || signIn_info.getTcId()==null || signIn_info.getTeacherId()==null){
            return SystemResponse.badArgument();
        }
        BufferedImage image = null;
        try {
            image = signInService.GetSignInImage(signIn_info.getSiiId(), signIn_info.getStartTime(), signIn_info.getEndTime());
        } catch (Exception e) {
            e.printStackTrace();
            return SystemResponse.fail(-1,e.getMessage());
        }

        /**以流的形式输出到前端*/
        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //将验证码放到HttpSession里面
        LOGGER.info("课程"+signIn_info.getSiiId()+"的签到二维码已经生成");
        //设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");

        ImageIO.write(image, "JPEG", response.getOutputStream());
        return null;
    }

    /**
     * 通过 pageInfo，userId 获得签到信息列表
     * @param body
     * @return
     */
    @RequiresPermissions("signIn_info:select")
    @RequestMapping("getSignInInfoListByUserId")
    public Object getSignInInfoList(@RequestBody String body){
        PageInfo pageInfo = null;
        Integer userId = null;
        try {
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            userId = JacksonUtil.parseInteger(body,"userId");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"Json转换错误");
        }
        if(userId == null || pageInfo==null || pageInfo.getPageNum()==null || pageInfo.getPageSize()==null){
            return SystemResponse.badArgument();
        }
        List<SignIn_infoList> signInInfoList = signInService.getSignInInfoList(pageInfo, userId);
        Map result = Maps.newHashMap();
        result.put("signInInfoList",signInInfoList);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 userId 获得签到信息总数
     * @param userId
     * @return
     */
    @RequiresPermissions("signIn_info:select")
    @RequestMapping("getSignInInfoCount")
    public Object getSignInInfoCount(@RequestParam("userId")Integer userId){

        if(userId == null){
            return SystemResponse.badArgument();
        }
        Integer count = signInService.getSignInInfoCount(userId);
        Map result = Maps.newHashMap();
        result.put("count",count);

        return SystemResponse.ok(result);
    }

    /**
     * 通过 siiIdList 批量删除对应的signIn_info和signIn_user
     * @param body
     * @return
     */
    @RequiresPermissions("signIn_info:delete")
    @RequestMapping("deleteSignInInfo")
    public Object deleteSignInInfo(@RequestBody String body){
        List<Integer> siiIdList = null;
        try {
            siiIdList = JacksonUtil.parseIntegerList(body,"siiIdList");
            if(siiIdList==null || siiIdList.size()==0){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        signInService.deleteSignIn_info(siiIdList);
        signInService.deleteSignIn_user(siiIdList);
        return SystemResponse.ok();
    }

    /**
     * 通过 siiId,pageInfo 获得 签到学生的列表
     * @param body
     * @return
     */
    @RequiresPermissions("signIn_user:select")
    @RequestMapping("getSignIn_userList")
    public Object getSignIn_userList(@RequestBody String body){
        Integer siiId = null;
        PageInfo pageInfo = null;
        try {
            siiId = JacksonUtil.parseInteger(body,"siiId");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if(siiId == null || pageInfo == null || pageInfo.getPageNum()== null || pageInfo.getPageSize()==null){
                return SystemResponse.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"json转换错误");
        }
        List<SignIn_userList> signIn_userList = signInService.getSignIn_userList(pageInfo, siiId);
        Map result = Maps.newHashMap();
        result.put("signIn_userList",signIn_userList);

        return SystemResponse.ok(result);
    }

    /**
     * 通过 siiId 获得签到总记录数
     * @param siiId
     * @return
     */
    @RequiresPermissions("signIn_user:select")
    @RequestMapping("getSignIn_userCount")
    public Object getSignIn_userCount(@RequestParam("siiId")Integer siiId){
        if(siiId == null){
            return SystemResponse.badArgument();
        }
        Integer count = signInService.getSignIn_userCount(siiId);
        Map result = Maps.newHashMap();
        result.put("count",count);
        return SystemResponse.ok(result);
    }

    /**
     * 扫描二维码进行签到,将siiId,userId,siTime传入
     * @param signIn_user
     * @return
     */
    @RequestMapping("SignIn")
    public Object SignIn(@RequestBody SignIn_user signIn_user,HttpServletRequest httpServletRequest){
        System.out.println(signIn_user);
        if(signIn_user==null){
            return WeChatResponseUtil.fail(-1,"签到失败");
        }
        Integer count = signInService.selectSignIn_user(signIn_user);
        if(count > 0){
            Map result = Maps.newHashMap();
            result.put("result","请勿重复签到");
            return WeChatResponseUtil.ok(result);
        }else{
            signInService.SignIn(signIn_user);
            Map result = Maps.newHashMap();
            result.put("result","签到成功");
            return WeChatResponseUtil.ok(result);
        }
    }
}
