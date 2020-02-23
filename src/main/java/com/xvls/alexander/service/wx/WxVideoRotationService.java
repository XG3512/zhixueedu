package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.WxVideoRotation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface WxVideoRotationService {

    /**
     * 获得 视频轮播图 信息
     * @return
     */
    List<WxVideoRotation> getVideoRotation();

    /**
     * 更新 视频轮播图 基本信息
     * @param file
     * @param wxVideoRotation
     */
    void updateVideoRotation(MultipartFile file, WxVideoRotation wxVideoRotation) throws IOException, NoSuchAlgorithmException;
}
