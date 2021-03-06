package com.xvls.alexander.service;

import com.xvls.alexander.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

public interface SignInService {

    /**
     * 生成签到图片
     * @param siiId
     * @param startTime
     * @param endTime
     * @return
     */
    BufferedImage GetSignInImage(Integer siiId, Date startTime, Date endTime) throws IOException, NoSuchAlgorithmException;

    /**
     * 插入 签到 信息
     * @param signIn_info
     * @return
     */
    Integer insertSignInfo(SignIn_info signIn_info);

    /**
     * 通过 siiId 获得签到信息
     * @param siiId
     * @return
     */
    SignIn_info getSignIn_infoBySiiId(Integer siiId);

    /**
     * 通过 pageInfo，userId 获得签到信息列表
     * @param pageInfo
     * @param userId
     * @return
     */
    List<SignIn_infoList> getSignInInfoList(PageInfo pageInfo, Integer userId);

    /**
     * 通过 userId 获得签到信息总数
     * @param userId
     * @return
     */
    Integer getSignInInfoCount(Integer userId);

    /**
     * 通过 siiId , pageInfo 获得 签到学生的列表
     * @param pageInfo
     * @param siiId
     * @return
     */
    List<SignIn_userList> getSignIn_userList(PageInfo pageInfo,Integer siiId);

    /**
     * 通过 siiIdList 删除对应的SignIn_info
     * @param siiIdList
     */
    void deleteSignIn_info(List<Integer> siiIdList);

    /**
     * 通过 siiId 删除对应的SignIn_user
     * @param siiIdList
     */
    void deleteSignIn_user(List<Integer> siiIdList);

    /**
     * 用户签到
     */
    void SignIn(SignIn_user signIn_user);

    /**
     * 通过 siiId 获得签到总记录数
     * @param siiId
     * @return
     */
    Integer getSignIn_userCount(Integer siiId);

    /**
     * 通过 signIn_user 获得对应签到者的数量
     * @param signIn_user
     * @return
     */
    Integer selectSignIn_user(SignIn_user signIn_user);
}
