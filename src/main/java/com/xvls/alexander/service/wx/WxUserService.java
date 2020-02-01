package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.WxUserInfo;

public interface WxUserService {

    WxUserInfo getWxStudentInfoByOpenId(String OpenId);

    WxUserInfo getWxStudentInfoByUserNum(String user_num);

    void saveWxStudentInfo(WxUserInfo wxUserInfo);
}
