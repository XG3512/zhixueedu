package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.TeacherMainPage;
import com.xvls.alexander.entity.wx.Users;

import java.util.List;

public interface WxUsersService {

    Users getWxStudentInfoByUserNum(String user_num);

    void saveWxStudentInfo(Users users);

    Integer getUserId(String userNum,Integer schoolId,String role);

    /**
     * 通过 userNum,schoolId,role 获得用户信息
     * @param userNum
     * @param schoolId
     * @param role
     * @return
     */
    Users getUserInfo(String userNum,Integer schoolId,String role);

    /**
     * 通过 userNum,role 获得用户信息（系统管理员）
     * @param userNum
     * @param role
     * @return
     */
    Users getUserInfo(String userNum,String role);

    /**
     * 通过 userId 获得用户信息
     * @param userId
     * @return
     */
    Users getUserInfoByUserId(Integer userId);

    Users getWxStudentInfoByUserId(Integer userId);

    Users getUsersByWxUserId(Integer wxUserId);

    void updateUsersWxUserIdByUserId(Integer userId , Integer wxUserId);

    void addFansNum(Integer teacherId);

    /**
     * 减少粉丝数
     */
    void decreaseFansNum(Integer teacherId);

    /**
     * 更新教师 点赞、收藏 数
     * @param teacherId
     */
    void updateTeacherGoodCollectionNum(Integer teacherId);

    /**
     * 更新教师背景图片
     */
    void updateTeacherBackground(Integer teacherId,String file_url);

    /**
     * 更新用户头像
     * @param userId
     * @param file_url
     */
    void updateUserIcon(Integer userId,String file_url);

    /**
     * 通过 wxUserId和teacherId 获得教师主页的教师信息
     * @param wxUserId
     * @param teacherId
     * @return
     */
    TeacherMainPage getTeacherMainPage(Integer wxUserId,Integer teacherId);

    /**
     * 通过 wxUserId 获得关注教师列表
     * @param wxUserId
     * @return
     */
    List<TeacherMainPage> getFollowTeacherList(Integer wxUserId);


}
