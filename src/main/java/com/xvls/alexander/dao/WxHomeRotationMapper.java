package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.WxHomeRotation;
import org.springframework.stereotype.Repository;

@Repository
public interface WxHomeRotationMapper extends BaseMapper<WxHomeRotation> {

    /**
     * 通过 homeRotationId，source 更改轮播图图片
     * @param homeRotationId
     * @param source
     */
    void updateHomeRotationSourse(Integer homeRotationId,String source);

    /**
     * 通过 homeRotationId，belongType，belongId 更新轮播图信息
     * @param homeRotationId
     * @param belongType
     * @param belongId
     */
    void updateHomeRotationInfo(Integer homeRotationId,String belongType,Integer belongId);
}
