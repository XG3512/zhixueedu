package com.xvls.alexander.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxV_labelMapper;
import com.xvls.alexander.entity.wx.V_label;
import com.xvls.alexander.service.V_labelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 视频主页-标签
 */
@Service
public class V_labelServiceImpl implements V_labelService {

    @Autowired
    WxV_labelMapper wxV_labelMapper;

    /**
     * 为视频主页插入标签
     * @param videoMainId
     * @param v_labelIdList
     */
    @Override
    public void insertV_labels(Integer videoMainId, List<Integer> v_labelIdList) {
        wxV_labelMapper.insertV_labels(videoMainId,v_labelIdList);
    }

    /**
     * 更新标签
     * @param videoMainId
     * @param v_labelIdList
     */
    @Override
    public void updateV_labels(Integer videoMainId, List<Integer> v_labelIdList) {
        deleteV_labelsByMainId(videoMainId);
        insertV_labels(videoMainId,v_labelIdList);
    }

    /**
     * 通过 videoMainId 删除视频标签
     * @param videoMainId
     */
    @Override
    public void deleteV_labelsByMainId(Integer videoMainId) {
        QueryWrapper<V_label> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_main_id",videoMainId);
        wxV_labelMapper.delete(queryWrapper);
    }
}
