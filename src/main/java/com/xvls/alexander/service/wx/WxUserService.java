package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.UserInfo;
import com.xvls.alexander.entity.wx.WxUser;

public interface WxUserService {

    WxUser getWxUserInfoByOpenId(String openId);

    Integer insertNewWxUser(WxUser wxUser);

    void updateLoginInfo(Integer wxUserId, UserInfo userInfo);
}
