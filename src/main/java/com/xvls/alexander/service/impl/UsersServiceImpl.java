package com.xvls.alexander.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.UsersMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.service.RoleService;
import com.xvls.alexander.service.UsersService;
import com.xvls.alexander.service.Video_mainService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersMapper usersMapper;
    @Autowired
    Video_mainService video_mainService;
    @Autowired
    RoleService roleService;

    @Override
    public List<Users> getUsersList(PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return usersMapper.getUsersList(new PageInfo(pageNum,pageSize));
    }

    /**
     * 后台管理端 通过 userId 获得用户信息
     * @param userId
     * @return
     */
    @Override
    public Users system_getUsersInfoById(Integer userId) {
        return usersMapper.system_getUsersInfoById(userId);
    }

    /**
     * 获得用户总数
     * @return
     */
    @Override
    public Integer getUsersCount() {
        return usersMapper.selectCount(null);
    }

    /**
     * 微信端通过 userId 获取个人信息
     * @param userId
     * @return
     */
    @Override
    public Users getWxUsersInfo(Integer userId) {
        return usersMapper.getWxUserInfoByUserId(userId);
    }

    /**
     * 微信端 修改个人信息
     * @param userId
     * @param phone
     * @param mail
     * @param motto
     */
    @Override
    public void wxUpdatePersonalInfo(Integer userId, String phone, String mail, String motto) {
        usersMapper.wxUpdatePersonalInfo(userId,phone,mail,motto);
    }

    /**
     * 微信端通过 userId 修改password
     * @param userId
     */
    @Override
    public void wxUpdatePassword(Integer userId,String password) {
        String salt = UUID.randomUUID().toString();
        salt = salt.replaceAll("-","");
        password = generatePassword(password,salt);
        System.out.println(userId+" 修改了密码，password:"+password+" salt:"+salt);
        usersMapper.wxUpdatePassword(userId,password,salt);
    }

    /**
     * 微信端 修改phone
     * @param userId
     * @param phone
     */
    @Override
    public void wxUpdatePhone(Integer userId, String phone) {
        usersMapper.wxUpdatePhone(userId,phone);
    }

    /**
     * 微信端 修改mail
     * @param userId
     * @param mail
     */
    @Override
    public void wxUpdateMail(Integer userId, String mail) {
        usersMapper.wxUpdateMail(userId,mail);
    }

    /**
     * 微信端 修改motto
     * @param userId
     * @param motto
     */
    @Override
    public void wxUpdateMotto(Integer userId, String motto) {
        usersMapper.wxUpdateMotto(userId,motto);
    }

    /**
     * 增加用户
     * @param users
     * @return
     */
    @Override
    public Integer addUser(Users users) {
        String salt = UUID.randomUUID().toString();
        salt = salt.replaceAll("-","");
        users.setPassword(generatePassword(users.getPassword(),salt));
        users.setSalt(salt);
        int insert = usersMapper.insert(users);
        return users.getUserId();
    }

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
    @Override
    public void updateUsersInfo(Integer userId,String userNum,Integer schoolId,Integer departmentId,Integer majorId,Integer classId,String userName,
                                String sex,String nation,Integer grade,String idCard,String phone,String mail,String address,String motto){
        usersMapper.updateUsersInfo(userId, userNum, schoolId, departmentId, majorId, classId, userName, sex, nation, grade, idCard, phone, mail,address, motto);
    }

    /**
     * 通过 userIdList 批量删除用户、对应的视频、角色信息
     * @param userIdList
     */
    @Override
    public void deleteUsers(List<Integer> userIdList) {
        usersMapper.deleteUsers(userIdList);
        video_mainService.deleteVideoByUserIdList(userIdList);
        video_mainService.deleteVideoMainPageByUserIdList(userIdList);
        roleService.deleteUser_roleByUserId(userIdList);
    }

    public String generatePassword(String password,String salt){
        //加密：MD5
        Md5Hash md5Hash = new Md5Hash(password, salt,6);
        return md5Hash.toHex();
    }
}
