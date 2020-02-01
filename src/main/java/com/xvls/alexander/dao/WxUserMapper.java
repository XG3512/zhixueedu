package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.WxUserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface WxUserMapper extends BaseMapper<WxUserInfo> {

    WxUserInfo getWxStudentInfoByOpenId(String OpenId);

    WxUserInfo getWxStudentInfoByUserNum(String user_num);//加学校！！！

    //void saveWxStudentInfo(WxUserInfo wxUserInfo);
}
