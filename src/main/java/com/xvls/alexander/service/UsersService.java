package com.xvls.alexander.service;

import com.xvls.alexander.entity.wx.Users;

public interface UsersService {

    /**
     * 微信端通过 userId 获取个人信息
     * @param userId
     * @return
     */
    Users getWxUsersInfo(Integer userId);

    /**
     * 微信端 修改个人信息
     * @param userId
     * @param phone
     * @param mail
     * @param motto
     */
    void wxUpdatePersonalInfo(Integer userId,String phone,String mail,String motto);

    /**
     * 微信端通过 userId 修改password
     * @param userId
     */
    void wxUpdatePassword(Integer userId,String password);

    /**
     * 微信端 修改phone
     * @param userId
     * @param phone
     */
    void wxUpdatePhone(Integer userId,String phone);

    /**
     * 微信端 修改mail
     * @param userId
     * @param mail
     */
    void wxUpdateMail(Integer userId,String mail);

    /**
     * 微信端 修改motto
     * @param userId
     * @param motto
     */
    void wxUpdateMotto(Integer userId,String motto);

    String generatePassword(String password,String salt);
}
