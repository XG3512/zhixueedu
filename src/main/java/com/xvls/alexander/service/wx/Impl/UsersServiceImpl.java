package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.UsersMapper;
import com.xvls.alexander.entity.wx.TeacherMainPage;
import com.xvls.alexander.entity.wx.UserInfo;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.service.wx.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 通过 userNum,schoolId,role 获得用户信息
     * @param userNum
     * @param schoolId
     * @param role
     * @return
     */
    @Override
    public Users getUserInfo(String userNum, Integer schoolId, String role) {
        return usersMapper.getUserInfo(userNum,schoolId,role);
    }

    /**
     * 通过 userId 获得用户信息
     * @param userId
     * @return
     */
    @Override
    public Users getUserInfoByUserId(Integer userId) {
        return usersMapper.getUserInfoByUserId(userId);
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

    /**
     * 更新教师 点赞、收藏 数
     * @param teacherId
     */
    @Override
    public void updateTeacherGoodCollectionNum(Integer teacherId) {
        usersMapper.updateTeacherGoodCollectionNum(teacherId);
    }

    @Override
    public void updateTeacherBackground(Integer teacherId, String file_url) {
        usersMapper.updateTeacherBackground(teacherId,file_url);
    }

    /**
     * 更新用户头像
     * @param userId
     * @param file_url
     */
    @Override
    public void updateUserIcon(Integer userId, String file_url) {
        usersMapper.updateUserIcon(userId,file_url);
    }

    /**
     * 通过 wxUserId和teacherId 获得教师主页的教师信息
     * @param wxUserId
     * @param teacherId
     * @return
     */
    @Override
    public TeacherMainPage getTeacherMainPage(Integer wxUserId, Integer teacherId) {
        this.updateTeacherGoodCollectionNum(teacherId);
        return usersMapper.getTeacherMainPage(wxUserId,teacherId);
    }

    /**
     * 通过 wxUserId 获得关注教师列表
     * @param wxUserId
     * @return
     */
    @Override
    public List<TeacherMainPage> getFollowTeacherList(Integer wxUserId) {
        return usersMapper.getFollowTeacherList(wxUserId);
    }

}
