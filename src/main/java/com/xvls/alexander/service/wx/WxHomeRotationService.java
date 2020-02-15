package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.entity.wx.WxHomeRotation;

import java.util.List;

public interface WxHomeRotationService {

    /**
     * 获得所有的轮播图信息
     * @return
     */
    List<WxHomeRotation> getAllHomeRotation();

    /**
     * 通过Id修改轮播图信息
     */
    void updateHomeRotation(WxHomeRotation wxHomeRotation);

    /**
     * 获得主页的视频列表
     */
    List<Video_main> getHomePageVideoList(Integer wxUserId);
}
