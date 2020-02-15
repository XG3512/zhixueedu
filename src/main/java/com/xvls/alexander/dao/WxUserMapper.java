package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.UserInfo;
import com.xvls.alexander.entity.wx.WxUser;
import org.springframework.stereotype.Repository;

@Repository
public interface WxUserMapper extends BaseMapper<WxUser> {

    /**
     * 授权，更新微信用户信息
     * @param wxUserId
     * @param userInfo
     */
    void updateLoginInfo(Integer wxUserId, UserInfo userInfo);
}
