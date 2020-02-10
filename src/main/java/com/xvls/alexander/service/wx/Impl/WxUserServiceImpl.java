package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxUserMapper;
import com.xvls.alexander.entity.wx.WxUserInfo;
import com.xvls.alexander.service.wx.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserMapper wxUserMapper;

    /**
     * 通过OpenId获取用户信息
     * @param OpenId
     * @return
     */
    @Override
    public WxUserInfo getWxStudentInfoByOpenId(String OpenId) {
        return wxUserMapper.getWxStudentInfoByOpenId(OpenId);
    }

    /**
     * 根据 学号+学校 获取用户信息
     * @param user_num
     * @return
     */
    @Override
    public WxUserInfo getWxStudentInfoByUserNum(String user_num) {
        return wxUserMapper.getWxStudentInfoByUserNum(user_num);
    }

    /**
     * 通过MyBatisPlus将用户保存
     * @param wxUserInfo
     */
    @Override
    public void saveWxStudentInfo(WxUserInfo wxUserInfo) {
        wxUserMapper.updateById(wxUserInfo);
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
        return wxUserMapper.getUserId(userNum,schoolId,role);
    }

    @Override
    public WxUserInfo getWxStudentInfoByUserId(Integer userId) {
        return wxUserMapper.getWxStudentInfoByUserId(userId);
    }


}
