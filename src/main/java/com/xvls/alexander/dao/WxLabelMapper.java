package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.Label;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WxLabelMapper extends BaseMapper<Label> {

    /**
     * 通过 wxUserId 获取顶部标签列表
     * @param wxUserId
     * @return
     */
    List<Label> getLabelListByWxUserId(Integer wxUserId);

    /**
     * 通过 wxUserId和标签Id列表 删除多个标签
     */
    void deleteLabelByIds(Integer wxUserId,List<Integer> labelIdList);

    /**
     * 通过 wxUserId,labelId 添加标签
     * @param wxUserId
     * @param labelId
     */
    void addLabelById(Integer wxUserId, Integer labelId, Date createTime);
}
