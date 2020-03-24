package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxNoticeMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Notice;
import com.xvls.alexander.service.wx.WxNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: 2020/2/9 点赞、收藏数的字符化，并放入数据库中
@Service
public class WxNoticeServiceImpl implements WxNoticeService {

    @Autowired
    WxNoticeMapper wxNoticeMapper;

    /**
     * 通过 schoolId，pageInfo 获取学校的通知信息
     * @param schoolId
     * @param pageInfo
     * @return
     */
    @Override
    public List<Notice> getNoticeListBySchoolId(Integer schoolId,PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return wxNoticeMapper.getNoticeListBySchoolId(schoolId,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 类别 和 PageInfo 获得通知列表
     * @param classification
     * @return
     */
    @Override
    public List<Notice> getNoticeListByClassificationPage(String classification, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return wxNoticeMapper.getNoticeListByClassificationPage(classification,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 noticeId 获取公告详情信息
     * @param noticeId
     * @return
     */
    @Override
    public Notice getNoticeById(Integer noticeId) {
        return wxNoticeMapper.getNoticeById(noticeId);
    }

    /**
     * 通过分页，获得最新公告信息
     * @return
     */
    @Override
    public List<Notice> getLatestNoticeByPage(PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return wxNoticeMapper.getLatestNoticeByPage(new PageInfo(pageNum,pageSize));
    }

    /**
     * 公告点赞数增加
     * @param noticeId
     */
    @Override
    public void addNoticeGoodNum(Integer noticeId) {
        wxNoticeMapper.addNoticeGoodNum(noticeId);
    }

    /**
     * 通知阅读量增加
     * @param noticeId
     */
    @Override
    public void addNoticeReadNum(Integer noticeId) {
        wxNoticeMapper.addNoticeReadNum(noticeId);
    }

    /**
     * 通知点赞数减少
     * @param noticeId
     */
    @Override
    public void decreaseNoticeGoodNum(Integer noticeId) {
        wxNoticeMapper.decreaseNoticeGoodNum(noticeId);
    }

    /**
     * 通过 pageInfo 获得通知列表
     * @param pageInfo
     * @return
     */
    @Override
    public List<Notice> getNoticeList(PageInfo pageInfo,Integer userId) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return wxNoticeMapper.getNoticeList(new PageInfo(pageNum,pageSize),userId);
    }

    /**
     * 通过 userId 获得通知总条目数
     * @param userId
     * @return
     */
    @Override
    public Integer getNoticeCount(Integer userId) {
        return wxNoticeMapper.getNoticeCount(userId);
    }

    /**
     * 添加通知
     * @param notice
     * @return
     */
    @Override
    public Integer addNotice(Notice notice) {
        Integer insert = wxNoticeMapper.insert(notice);
        return notice.getNoticeId();
    }

    /**
     * 通过 noticeIdList 批量删除通知
     * @param noticeIdList
     */
    @Override
    public void deleteNotices(List<Integer> noticeIdList) {
        wxNoticeMapper.deleteNotices(noticeIdList);
    }

}
