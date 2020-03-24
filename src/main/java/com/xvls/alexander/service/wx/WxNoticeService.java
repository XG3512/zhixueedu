package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Notice;
import com.xvls.alexander.entity.wx.NoticeList;

import java.util.List;

/**
 * 通知Service
 */
public interface WxNoticeService {

    /**
     * 通过 schoolId，pageInfo 获取学校的通知信息
     * @param schoolId
     * @param pageInfo
     * @return
     */
    List<Notice> getNoticeListBySchoolId(Integer schoolId,PageInfo pageInfo);

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

    /**------------------------------后台管理端---------------------------------*/

    /**
     * 通过 pageInfo 获得通知列表
     * @param pageInfo
     * @return
     */
    List<Notice> getNoticeList(PageInfo pageInfo,Integer userId);

    /**
     * 通过 userId 获得通知总条目数
     * @param userId
     * @return
     */
    Integer getNoticeCount(Integer userId);

    /**
     * 添加通知
     * @param notice
     * @return
     */
    Integer addNotice(Notice notice);

    /**
     * 通过 noticeIdList 批量删除通知
     * @param noticeIdList
     */
    void deleteNotices(List<Integer> noticeIdList);


}
