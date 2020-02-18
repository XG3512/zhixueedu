package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.TeacherMainPage;
import com.xvls.alexander.entity.wx.Users;

public interface UsersService {

    Users getWxStudentInfoByUserNum(String user_num);

    void saveWxStudentInfo(Users users);

    Integer getUserId(String userNum,Integer schoolId,String role);

    Users getWxStudentInfoByUserId(Integer userId);

    Users getUsersByWxUserId(Integer wxUserId);

    void updateUsersWxUserIdByUserId(Integer userId , Integer wxUserId);

    void addFansNum(Integer teacherId);

    /**
     * 减少粉丝数
     */
    void decreaseFansNum(Integer teacherId);

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
}
