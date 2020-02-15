package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
