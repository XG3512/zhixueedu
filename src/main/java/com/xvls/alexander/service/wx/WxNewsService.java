package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.WxNews;

import java.util.List;


/**
 * 教务通知
 */
public interface WxNewsService {

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

    /**----------------------后台管理端-----------------------*/
    /**
     * 通过 userId,pageInfo 获得通知列表
     * @param userId
     * @param pageInfo
     * @return
     */
    List<WxNews> getNewsListById(Integer userId, PageInfo pageInfo);

    /**
     * 通过 userId，content，pageInfo 进行模糊搜索
     * @param userId
     * @param content
     * @param pageInfo
     * @return
     */
    List<WxNews> searchNews(Integer userId,String content,PageInfo pageInfo);

    /**
     * 通过 userId，content 获得通知数量
     * @param userId
     * @param content
     * @return
     */
    Integer getSearchNewsCount(Integer userId , String content);

    /**
     * 添加通知
     * @param wxNews
     * @return
     */
    Integer addNews(WxNews wxNews);

    /**
     * 通过 userId 获得通知总数
     * @param userId
     * @return
     */
    Integer getNewsCount(Integer userId);

    /**
     * 获得通知总数
     * @return
     */
    Integer getAllNewsCount();

    /**
     * 通过 newsIdList数组 批量删除学校通知
     * @param newsIdList
     */
    void deleteNews(List<Integer> newsIdList);
}
