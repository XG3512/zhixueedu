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
    List<Notice> getNoticeListBySchoolId(Integer schoolId);


    /**********************************************************/
    /**
     * 公告点赞数增加
     * @param noticeId
     */
    void addNoticeGoodNum(Integer noticeId);
    /**
     * 通知阅读量增加
     * @param noticeId
     */
    void addNoticeReadNum(Integer noticeId);
    /**
     * 通知点赞数减少
     * @param noticeId
     */
    void decreaseNoticeGoodNum(Integer noticeId);


}
