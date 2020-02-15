package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxNoticeMapper extends BaseMapper<Notice> {

    /**
     * 通过 类别 和 PageInfo 获得通知列表
     * @param classification
     * @return
     */
    List<Notice> getNoticeListByClassificationPage(String classification, PageInfo pageInfo);

    /**
     * 通过 noticeId 获取公告详情信息
     * @param noticeId
     * @return
     */
    Notice getNoticeById(Integer noticeId);

    /**
     * 通过分页，获得最新公告信息
     * @return
     */
    List<Notice> getLatestNoticeByPage(PageInfo pageInfo);

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
