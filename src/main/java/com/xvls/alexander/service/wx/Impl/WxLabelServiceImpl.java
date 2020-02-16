package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxLabelMapper;
import com.xvls.alexander.dao.WxLabel_typeMapper;
import com.xvls.alexander.entity.wx.Label;
import com.xvls.alexander.entity.wx.Label_type;
import com.xvls.alexander.service.wx.WxLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WxLabelServiceImpl implements WxLabelService {

    @Autowired
    WxLabelMapper wxLabelMapper;
    @Autowired
    WxLabel_typeMapper wxLabel_typeMapper;

    /**
     * 获得所有标签类型下的所有标签
     * @return
     */
    @Override
    public List<Label_type> getAllLabelList() {
        return wxLabel_typeMapper.getAllLabelList();
    }

    /**
     * 通过 wxUserId 获取顶部标签列表
     * @param wxUserId
     * @return
     */
    @Override
    public List<Label> getLabelListByWxUserId(Integer wxUserId) {
        return wxLabelMapper.getLabelListByWxUserId(wxUserId);
    }

    /**
     * 通过 wxUserId和标签Id列表 删除多个标签
     * @param wxUserId
     * @param labelIdList
     */
    @Override
    public void deleteLabelByIds(Integer wxUserId, List<Integer> labelIdList) {
        wxLabelMapper.deleteLabelByIds(wxUserId,labelIdList);
    }

    /**
     * 通过 wxUserId,labelId 添加标签
     * @param wxUserId
     * @param labelId
     */
    @Override
    public void addLabelById(Integer wxUserId, Integer labelId) {

        wxLabelMapper.addLabelById(wxUserId,labelId,new Date());
    }
}
