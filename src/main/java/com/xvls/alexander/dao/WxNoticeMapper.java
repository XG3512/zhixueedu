package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.Notice;
import org.springframework.stereotype.Repository;

@Repository
public interface WxNoticeMapper extends BaseMapper<Notice> {

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
