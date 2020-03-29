package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.WxVideoRotation;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 视频轮播图
 */
@Repository
public interface WxVideoRotationMapper extends BaseMapper<WxVideoRotation> {

    /**
     * 通过 videoRotationId，videoMainId 修改视频信息
     * @param videoRotationId
     * @param videoMainId
     */
    void updateVideoRotation(Integer videoRotationId,Integer videoMainId);

    /**
     * 更新 视频轮播图 信息
     * @param videoRotationId
     * @param source
     */
    void updateVideoRotationImage(Integer videoRotationId,String source);
}
