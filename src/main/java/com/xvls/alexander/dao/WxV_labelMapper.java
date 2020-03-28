package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.V_label;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 视频-标签
 */
@Repository
public interface WxV_labelMapper extends BaseMapper<V_label> {

    void insertV_labels(Integer videoMainId,List<Integer> v_labelIdList);
}
