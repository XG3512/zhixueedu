package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.UsersMapper;
import com.xvls.alexander.entity.wx.UserInfo;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.service.wx.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 根据 学号+学校 获取用户信息
     * @param user_num
     * @return
     */
    @Override
    public Users getWxStudentInfoByUserNum(String user_num) {
        return usersMapper.getWxStudentInfoByUserNum(user_num);
    }

    /**
     * 通过MyBatisPlus将用户保存
     * @param users
     */
    @Override
    public void saveWxStudentInfo(Users users) {
        usersMapper.updateById(users);
    }

    /**
     * 通过userNum,schoolId,role获取userId
     * @param userNum
     * @param schoolId
     * @param role
     * @return
     */
    @Override
    public Integer getUserId(String userNum, Integer schoolId, String role) {
        return usersMapper.getUserId(userNum,schoolId,role);
    }

    @Override
    public Users getWxStudentInfoByUserId(Integer userId) {
        return usersMapper.getWxStudentInfoByUserId(userId);
    }

    /**
     * 通过wxUserId获得对应的Users
     * @param wxUserId
     * @return
     */
    @Override
    public Users getUsersByWxUserId(Integer wxUserId) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wx_user_id",wxUserId);
        return usersMapper.selectOne(queryWrapper);
    }

    /**
     * 通过 userId 绑定微信账户
     * @param userId
     * @param wxUserId
     */
    @Override
    public void updateUsersWxUserIdByUserId(Integer userId , Integer wxUserId) {
        usersMapper.updateUsersWxUserIdByUserId(userId,wxUserId);
    }

    /**
     * 增加粉丝数
     */
    @Override
    public void addFansNum(Integer teacherId) {
        usersMapper.addFansNum(teacherId);
    }

    /**
     * 减少粉丝数
     */
    @Override
    public void decreaseFansNum(Integer teacherId) {
        usersMapper.decreaseFansNum(teacherId);
    }


}
