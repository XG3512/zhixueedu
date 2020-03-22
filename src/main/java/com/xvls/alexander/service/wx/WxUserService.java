package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.UserInfo;
import com.xvls.alexander.entity.wx.WxUser;

public interface WxUserService {

    WxUser getWxUserInfoByOpenId(String openId);

    Integer insertNewWxUser(WxUser wxUser);

    void updateLoginInfo(Integer wxUserId, UserInfo userInfo);

    /**
     * 解除学号和微信账号的绑定
     * @param userId
     */
    void wxUserLogout(Integer userId);
}
