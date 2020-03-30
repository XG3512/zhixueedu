package com.xvls.alexander.service;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Users;

import java.util.List;

public interface UsersService {

    /**
     * 通过 pageInfo 获得用户信息列表
     * @param pageInfo
     * @return
     */
    List<Users> getUsersList(PageInfo pageInfo);

    /**
     * 后台管理端 通过 userId 获得用户信息
     * @param userId
     * @return
     */
    Users system_getUsersInfoById(Integer userId);

    /**
     * 获得用户总数
     * @return
     */
    Integer getUsersCount();

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

    /**
     * 增加用户
     * @param users
     * @return
     */
    Integer addUser(Users users);

    /**
     * 修改用户信息
     * @param userId
     * @param userNum
     * @param schoolId
     * @param departmentId
     * @param majorId
     * @param classId
     * @param userName
     * @param sex
     * @param nation
     * @param grade
     * @param idCard
     * @param phone
     * @param mail
     * @param address
     * @param motto
     */
    void updateUsersInfo(Integer userId,String userNum,Integer schoolId,Integer departmentId,Integer majorId,Integer classId,String userName,
                         String sex,String nation,Integer grade,String idCard,String phone,String mail,String address,String motto);

    /**
     * 通过 userIdList 批量删除用户、对应的视频、角色信息
     * @param userIdList
     */
    void deleteUsers(List<Integer> userIdList);

    String generatePassword(String password,String salt);
}
