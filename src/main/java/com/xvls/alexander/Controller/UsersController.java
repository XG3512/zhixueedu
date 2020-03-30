package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.service.RoleService;
import com.xvls.alexander.service.UsersService;
import com.xvls.alexander.service.wx.WxUsersService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.SystemResponse;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/users")
public class UsersController {

    @Autowired
    UsersService usersService;
    @Autowired
    WxUsersService wxUsersService;
    @Autowired
    QiniuFileUtil qiniuFileUtil;
    @Autowired
    RoleService roleService;

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

    /*********************************后台管理端**************************************/
    /**
     * 通过 pageInfo 获得用户信息列表
     * @param body
     * @return
     */
    @RequiresPermissions("users:select")
    @RequestMapping("getUsersList")
    public Object getUsersList(@RequestBody String body){
        PageInfo pageInfo = null;
        pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
        List<Users> usersList = usersService.getUsersList(pageInfo);
        Map result = Maps.newHashMap();
        result.put("usersList",usersList);
        return SystemResponse.ok(result);
    }

    /**
     * 获得用户总数
     * @return
     */
    @RequestMapping("getUsersCount")
    public Object getUsersCount(){
        Integer usersCount = usersService.getUsersCount();
        Map result = Maps.newHashMap();
        result.put("usersCount",usersCount);
        return SystemResponse.ok(result);
    }

    /**
     * 后台管理端 通过 userId 获得用户信息
     * @param userId
     * @return
     */
    @RequiresPermissions("users:select")
    @RequestMapping("system_getUsersInfoById")
    public Object system_getUsersInfoById(@RequestParam("userId")Integer userId){
        if(userId == null){
            return SystemResponse.badArgument();
        }
        Users usersInfo = usersService.system_getUsersInfoById(userId);
        Map result = Maps.newHashMap();
        result.put("usersInfo",usersInfo);
        return SystemResponse.ok(result);
    }

    /**
     * 添加用户
     * @param icon
     * @param background
     * @param httpServletRequest
     * @return
     */
    @RequiresPermissions("users:add")
    @RequestMapping("addUser")
    public Object addUser(@RequestParam("icon") MultipartFile icon, @RequestParam("background")MultipartFile background, HttpServletRequest httpServletRequest){
        if(icon == null || background == null){
            return SystemResponse.badArgument();
        }
        Users users = null;
        List<Integer> roleIdList = null;
        try {
            users = JacksonUtil.parseObject(httpServletRequest.getParameter("user"),Users.class);
            roleIdList = JacksonUtil.parseObject(httpServletRequest.getParameter("roleIdList"),List.class);
            System.out.println("users: "+users);
            System.out.println("roleIdList: "+roleIdList);
            if(users == null || users.getUserNum() == null || users.getPassword() == null || roleIdList == null || roleIdList.size() == 0){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        File_download file_download1 = null;
        File_download file_download2 = null;
        try {
            file_download1 = qiniuFileUtil.upload(icon);
            file_download2 = qiniuFileUtil.upload(background);
            if (file_download1 == null || file_download2 == null){
                return SystemResponse.fail();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.fail(-1,"文件上传失败");
        }
        users.setIcon(file_download1.getFileUrl());
        users.setBackground(file_download2.getFileUrl());
        users.setFansNum(0);
        users.setGoodNum(0);
        users.setCollectionNum(0);

        Integer userId = usersService.addUser(users);
        roleService.updateUserRole(userId,roleIdList);

        Map result = Maps.newHashMap();
        result.put("userId",userId);

        return SystemResponse.ok(result);
    }

    /**
     * 直接修改密码
     * @param password
     * @return
     */
    @RequiresPermissions("users:update")
    @RequestMapping("updatePassword")
    public Object updatePassword(@RequestParam("userId")Integer userId,@RequestParam("password") String password){
        if(userId == null || password == null){
            return SystemResponse.badArgument();
        }
        usersService.wxUpdatePassword(userId,password);
        return SystemResponse.ok();
    }

    /**
     * 修改用户信息
     * @param body
     * @return
     */
    @RequiresPermissions("users:update")
    @RequestMapping("updateUsersInfo")
    public Object updateUsersInfo(@RequestBody String body){
        Integer userId = null;
        String userNum = null;
        Integer schoolId = null;
        Integer departmentId = null;
        Integer majorId = null;
        Integer classId = null;
        String userName = null;
        String sex = null;
        String nation = null;
        Integer grade = null;
        String idCard = null;
        String phone = null;
        String mail = null;
        String address = null;
        String motto = null;

        try {
            userId = JacksonUtil.parseInteger(body,"userId");
            userNum = JacksonUtil.parseString(body,"userNum");
            schoolId = JacksonUtil.parseInteger(body,"schoolId");
            departmentId = JacksonUtil.parseInteger(body,"departmentId");
            majorId = JacksonUtil.parseInteger(body,"majorId");
            classId = JacksonUtil.parseInteger(body,"classId");
            userName = JacksonUtil.parseString(body,"userName");
            sex = JacksonUtil.parseString(body,"sex");
            nation = JacksonUtil.parseString(body,"nation");
            grade = JacksonUtil.parseInteger(body,"grade");
            idCard = JacksonUtil.parseString(body,"idCard");
            phone = JacksonUtil.parseString(body,"phone");
            mail = JacksonUtil.parseString(body,"mail");
            address = JacksonUtil.parseString(body,"address");
            motto = JacksonUtil.parseString(body,"motto");
            if(userId == null || userNum == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        usersService.updateUsersInfo(userId,userNum,schoolId,departmentId,majorId,classId,userName,sex,nation,grade,idCard,phone,mail,address,motto);
        return SystemResponse.ok();
    }

    /**
     * 通过 userId、roleIdList 更改用户角色信息
     * @param body
     * @return
     */
    @RequiresPermissions("users:update")
    @RequestMapping("updateUserRole")
    public Object updateUserRole(@RequestBody String body){
        Integer userId = null;
        List<Integer> roleIdList = null;
        try {
            userId = JacksonUtil.parseInteger(body,"userId");
            roleIdList = JacksonUtil.parseIntegerList(body,"roleIdList");
            if(userId == null || roleIdList == null || roleIdList.size() == 0){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        roleService.updateUserRole(userId,roleIdList);
        return SystemResponse.ok();
    }

    /**
     * 通过 userIdList 批量删除用户、对应的视频、角色信息
     * @param body
     * @return
     */
    @RequiresPermissions("users:delete")
    @RequestMapping("deleteUsers")
    public Object deleteUsers(@RequestBody String body){
        List<Integer> userIdList = null;
        try {
            userIdList = JacksonUtil.parseIntegerList(body,"userIdList");
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        usersService.deleteUsers(userIdList);
        return SystemResponse.ok();
    }

}
