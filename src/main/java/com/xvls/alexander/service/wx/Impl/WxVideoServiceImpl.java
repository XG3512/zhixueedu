package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxVideoMapper;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.wx.WxVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxVideoServiceImpl implements WxVideoService {

    @Autowired
    WxVideoMapper wxVideoMapper;

    /**
     * 获得视频列表信息
     * @return
     */
    @Override
    public List<Video_main> getPublicVideoList() {
        return wxVideoMapper.getPublicVideoList();
    }

    /**
     * 通过schoolId获得学校的视频信息，在学校主页面
     * @param schoolId
     * @return
     */
    @Override
    public List<Video_main> getPublicVideoListBySchoolId(Integer schoolId) {
        return wxVideoMapper.getPublicVideoListBySchoolId(schoolId);
    }

    /**
     * 通过videoMainId,episodeId,获取该集视频的详细信息
     * @param videoMainId
     * @param episodeId
     * @return
     */
    @Override
    public Video getVideoInfoById(Integer videoMainId, Integer episodeId) {
        return wxVideoMapper.getVideoInfoById(videoMainId,episodeId);
    }

    /**
     * 通过videoMainId获取该主页面的视频信息
     * @param videoMainId
     * @return
     */
    @Override
    public Video_main getVideoMainInfo(Integer videoMainId) {
        return wxVideoMapper.getVideoMainInfo(videoMainId);
    }


}
