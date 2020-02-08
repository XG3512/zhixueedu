package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxNoticeMapper;
import com.xvls.alexander.entity.wx.Notice;
import com.xvls.alexander.service.wx.WxNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxNoticeServiceImpl implements WxNoticeService {

    @Autowired
    WxNoticeMapper wxNoticeMapper;

    /**
     * 通过schoolId获取学校的通知信息
     * @param schoolId
     * @return
     */
    @Override
    public List<Notice> getNoticeListBySchoolId(Integer schoolId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("school_id",schoolId);
        return wxNoticeMapper.selectList(queryWrapper);
    }
}
