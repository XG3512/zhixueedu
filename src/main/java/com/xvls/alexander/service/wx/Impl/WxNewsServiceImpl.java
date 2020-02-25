package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxNewsMapper;
import com.xvls.alexander.entity.wx.WxNews;
import com.xvls.alexander.service.wx.WxNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxNewsServiceImpl implements WxNewsService {

    @Autowired
    WxNewsMapper wxNewsMapper;

    /**
     * 通过 userId 获得教务通知
     * @param userId
     * @return
     */
    @Override
    public List<WxNews> getNewsListById(Integer userId) {
        return wxNewsMapper.getNewsListById(userId);
    }

    /**
     * 通过 newsId 获得教务通知详情信息
     * @param newsId
     * @return
     */
    @Override
    public WxNews getNewsInfoById(Integer newsId) {
        addReadNum(newsId);
        return wxNewsMapper.getNewsInfoById(newsId);
    }

    /**
     * 增加阅读量
     * @param newsId
     */
    @Override
    public void addReadNum(Integer newsId) {
        wxNewsMapper.addReadNum(newsId);
    }
}
