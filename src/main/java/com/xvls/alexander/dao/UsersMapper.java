package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.TeacherMainPage;
import com.xvls.alexander.entity.wx.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersMapper extends BaseMapper<Users> {


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

    Users getWxStudentInfoByUserNum(String user_num);//加学校！！！

    Integer getUserId(String userNum, Integer schoolId, String role);

    /**
     * 通过 userNum,schoolId,role 获得用户信息
     * @param userNum
     * @param schoolId
     * @param role
     * @return
     */
    Users getUserInfo(String userNum,Integer schoolId,String role);

    /**
     * 通过 userId 获得用户信息
     * @param userId
     * @return
     */
    Users getUserInfoByUserId(Integer userId);

    /**
     * 获得 wxUser 信息
     * @param userId
     * @return
     */
    Users getWxUserInfoByUserId(Integer userId);

    Users getWxStudentInfoByUserId(Integer userId);

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
     * 更新教师 点赞、收藏 数
     * @param teacherId
     */
    void updateTeacherGoodCollectionNum(Integer teacherId);

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

    /**
     * 通过 wxUserId 获得关注教师列表
     * @param wxUserId
     * @return
     */
    List<TeacherMainPage> getFollowTeacherList(Integer wxUserId);

    /**
     * 微信端 修改个人信息
     * @param userId
     * @param phone
     * @param mail
     * @param motto
     */
    void wxUpdatePersonalInfo(Integer userId,String phone,String mail,String motto);

    /**
     * 微信端通过 userId,password,salt 修改password
     * @param userId
     */
    void wxUpdatePassword(Integer userId,String password,String salt);

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
     * 解除学号和微信账号的绑定
     * @param userId
     */
    void wxUserLogout(Integer userId);

    /**
     * 通过 userIdList 批量删除用户信息
     * @param userIdList
     */
    void deleteUsers(List<Integer> userIdList);
}
