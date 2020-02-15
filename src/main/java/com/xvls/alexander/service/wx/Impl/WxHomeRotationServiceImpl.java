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
}
