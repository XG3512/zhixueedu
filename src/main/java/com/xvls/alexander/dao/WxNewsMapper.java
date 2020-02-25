package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.WxNews;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxNewsMapper extends BaseMapper<WxNews> {

    /**
     * 通过 userId 获得教务通知
     * @param userId
     * @return
     */
    List<WxNews> getNewsListById(Integer userId);

    /**
     * 通过 newsId 获得教务通知详情信息
     * @param newsId
     * @return
     */
    WxNews getNewsInfoById(Integer newsId);

    /**
     * 增加阅读量
     * @param newsId
     */
    void addReadNum(Integer newsId);
}
