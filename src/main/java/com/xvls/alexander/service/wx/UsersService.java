package com.xvls.alexander.service.wx;

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
}
