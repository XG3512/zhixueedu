package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxVideoRotationMapper;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.entity.wx.WxVideoRotation;
import com.xvls.alexander.service.wx.WxVideoRotationService;
import com.xvls.alexander.utils.QiniuFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class WxVideoRotationServiceImpl implements WxVideoRotationService {

    @Autowired
    WxVideoRotationMapper wxVideoRotationMapper;
    @Autowired
    QiniuFileUtil qiniuFileUtil;

    /**
     * 获得 视频轮播图 信息
     * @return
     */
    @Override
    public List<WxVideoRotation> getVideoRotation() {
        return wxVideoRotationMapper.selectList(null);
    }

    /**
     * 更新 视频轮播图 基本信息
     * @param file
     * @param wxVideoRotation
     */
    @Override
    public void updateVideoRotation(MultipartFile file, WxVideoRotation wxVideoRotation) throws IOException, NoSuchAlgorithmException {
        File_download file_download = qiniuFileUtil.upload(file);
        wxVideoRotation.setSource(file_download.getFileUrl());
        wxVideoRotationMapper.updateById(wxVideoRotation);
    }
}
