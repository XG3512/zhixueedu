package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Notice;

import java.util.List;

/**
 * 通知Service
 */
public interface WxNoticeService {

    /**
     * 通过schoolId获取学校的通知信息
     * @param schoolId
     * @return
     */
    public List<Notice> getNoticeListBySchoolId(Integer schoolId);
}
