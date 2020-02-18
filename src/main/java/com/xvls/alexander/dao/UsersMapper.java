package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.TeacherMainPage;
import com.xvls.alexander.entity.wx.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper extends BaseMapper<Users> {


    Users getWxStudentInfoByUserNum(String user_num);//加学校！！！

    Integer getUserId(String userNum, Integer schoolId, String role);

    Users getWxStudentInfoByUserId(Integer userId);

    /**
     * 通过 userId 绑定微信账户
     * @param userId
     */
    void updateUsersWxUserIdByUserId(Integer userId , Integer wxUserId);

    /**
     * 增加粉丝数
     */
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
     * 更新教师头像
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
    TeacherMainPage getTeacherMainPage(Integer wxUserId, Integer teacherId);
}
