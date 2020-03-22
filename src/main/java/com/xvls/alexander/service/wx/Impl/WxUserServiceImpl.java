package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.UsersMapper;
import com.xvls.alexander.dao.WxUserMapper;
import com.xvls.alexander.entity.wx.UserInfo;
import com.xvls.alexander.entity.wx.WxUser;
import com.xvls.alexander.service.wx.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    WxUserMapper wxUserMapper;
    @Autowired
    UsersMapper usersMapper;

    /**
     * 通过openid获得微信用户信息
     * @param openid
     * @return
     */
    @Override
    public WxUser getWxUserInfoByOpenId(String openid) {
        QueryWrapper<WxUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        return wxUserMapper.selectOne(queryWrapper);
    }

    /**
     * 插入新的用户数据
     * @param wxUser
     * @return
     */
    @Override
    public Integer insertNewWxUser(WxUser wxUser) {
        int insert = wxUserMapper.insert(wxUser);
        return wxUser.getWxUserId();
    }

    /**
     * 授权，更新微信用户信息
     * @param wxUserId
     * @param userInfo
     */
    @Override
    public void updateLoginInfo(Integer wxUserId, UserInfo userInfo) {
        wxUserMapper.updateLoginInfo(wxUserId,userInfo);
    }

    /**
     * 解除学号和微信账号的绑定
     * @param userId
     */
    @Override
    public void wxUserLogout(Integer userId) {
        usersMapper.wxUserLogout(userId);
    }
}
