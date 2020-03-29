package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxHomeRotationMapper;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.entity.wx.WxHomeRotation;
import com.xvls.alexander.service.wx.WxHomeRotationService;
import com.xvls.alexander.service.wx.WxVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxHomeRotationServiceImpl implements WxHomeRotationService {

    @Autowired
    WxHomeRotationMapper wxHomeRotationMapper;
    @Autowired
    WxVideoService wxVideoService;

    @Override
    public List<WxHomeRotation> getAllHomeRotation() {
        return wxHomeRotationMapper.selectList(null);
    }

    @Override
    public void updateHomeRotation(WxHomeRotation wxHomeRotation) {
        wxHomeRotationMapper.updateById(wxHomeRotation);
    }

    @Override
    public List<Video_main> getHomePageVideoList(Integer wxUserId) {
        return wxVideoService.getHomePageVideoList(wxUserId);
    }

    /**
     * 通过 homeRotationId，source 更改轮播图图片
     * @param homeRotationId
     * @param source
     */
    @Override
    public void updateHomeRotationSourse(Integer homeRotationId, String source) {
        wxHomeRotationMapper.updateHomeRotationSourse(homeRotationId,source);
    }

    /**
     * 通过 homeRotationId，belongType，belongId 更新轮播图信息
     * @param homeRotationId
     * @param belongType
     * @param belongId
     */
    @Override
    public void updateHomeRotationInfo(Integer homeRotationId, String belongType, Integer belongId) {
        wxHomeRotationMapper.updateHomeRotationInfo(homeRotationId, belongType, belongId);
    }
}
