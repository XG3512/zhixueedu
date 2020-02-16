package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Label;
import com.xvls.alexander.entity.wx.Label_type;

import java.util.List;

/**
 * 视频标签Service
 */
public interface WxLabelService {

    /**
     * 获得所有标签类型下的所有标签
     * @return
     */
    List<Label_type> getAllLabelList();

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
    void addLabelById(Integer wxUserId,Integer labelId);
}
