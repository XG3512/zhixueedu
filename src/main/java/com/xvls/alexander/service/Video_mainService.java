package com.xvls.alexander.service;

import com.xvls.alexander.entity.wx.Video_main;

/**
 * 后台管理端的视频Service
 */
public interface Video_mainService {

    /**
     * 上传 视频主页 信息
     * @param video_main
     */
    Integer uploadVideoMain(Video_main video_main);
}
