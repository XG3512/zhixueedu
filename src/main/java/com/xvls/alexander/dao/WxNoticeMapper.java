package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxNoticeMapper extends BaseMapper<Notice> {

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

    /**
     * 通过 wxUserId ，pageInfo 获得通知点赞列表
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<Notice> getNoticeGoodList(Integer wxUserId,PageInfo pageInfo);

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
     * 招生信息搜索
     * @param userId
     * @param content
     * @param pageInfo
     * @return
     */
    List<Notice> searchNotice(Integer userId,String content,PageInfo pageInfo);
    /**
     * 获得招生信息搜索总数
     * @param userId
     * @param content
     * @return
     */
    Integer getSearchNoticeCount(Integer userId,String content);

    /**
     * 通过 noticeIdList 批量删除通知
     * @param noticeIdList
     */
    void deleteNotices(List<Integer> noticeIdList);
}
