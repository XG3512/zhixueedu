package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxNoticeMapper;
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
}
