package com.xvls.alexander.service.impl;

import com.xvls.alexander.dao.UsersMapper;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.service.UsersService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersMapper usersMapper;

    /**
     * 微信端通过 userId 获取个人信息
     * @param userId
     * @return
     */
    @Override
    public Users getWxUsersInfo(Integer userId) {
        return usersMapper.getWxUserInfoByUserId(userId);
    }

    /**
     * 微信端 修改个人信息
     * @param userId
     * @param phone
     * @param mail
     * @param motto
     */
    @Override
    public void wxUpdatePersonalInfo(Integer userId, String phone, String mail, String motto) {
        usersMapper.wxUpdatePersonalInfo(userId,phone,mail,motto);
    }

    /**
     * 微信端通过 userId 修改password
     * @param userId
     */
    @Override
    public void wxUpdatePassword(Integer userId,String password) {
        String salt = UUID.randomUUID().toString();
        salt = salt.replaceAll("-","");
        password = generatePassword(password,salt);
        System.out.println(userId+" 修改了密码，password:"+password+" salt:"+salt);
        usersMapper.wxUpdatePassword(userId,password,salt);
    }

    /**
     * 微信端 修改phone
     * @param userId
     * @param phone
     */
    @Override
    public void wxUpdatePhone(Integer userId, String phone) {
        usersMapper.wxUpdatePhone(userId,phone);
    }

    /**
     * 微信端 修改mail
     * @param userId
     * @param mail
     */
    @Override
    public void wxUpdateMail(Integer userId, String mail) {
        usersMapper.wxUpdateMail(userId,mail);
    }

    /**
     * 微信端 修改motto
     * @param userId
     * @param motto
     */
    @Override
    public void wxUpdateMotto(Integer userId, String motto) {
        usersMapper.wxUpdateMotto(userId,motto);
    }

    public String generatePassword(String password,String salt){
        //加密：MD5
        Md5Hash md5Hash = new Md5Hash(password, salt,6);
        return md5Hash.toHex();
    }
}
