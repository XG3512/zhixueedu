package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxNewsMapper;
import com.xvls.alexander.entity.PageInfo;
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

    /**----------------------后台管理端-----------------------*/
    /**
     * 通过 userId,pageInfo 获得通知列表
     * @param userId
     * @param pageInfo
     * @return
     */
    @Override
    public List<WxNews> getNewsListById(Integer userId, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return wxNewsMapper.getNewsListByIdAndPage(userId,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 userId，content，pageInfo 进行模糊搜索
     * @param userId
     * @param content
     * @param pageInfo
     * @return
     */
    @Override
    public List<WxNews> searchNews(Integer userId , String content, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        content = "%"+content+"%";
        return wxNewsMapper.searchNews(userId,content,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 userId，content 获得通知数量
     * @param userId
     * @param content
     * @return
     */
    @Override
    public Integer getSearchNewsCount(Integer userId, String content) {
        content = "%"+content+"%";
        return wxNewsMapper.getSearchNewsCount(userId,content);
    }

    /**
     * 添加通知
     * @param wxNews
     * @return
     */
    @Override
    public Integer addNews(WxNews wxNews) {
        int insert = wxNewsMapper.insert(wxNews);
        return wxNews.getNewsId();
    }

    @Override
    public Integer getNewsCount(Integer userId) {
        return wxNewsMapper.getNewsCount(userId);
    }

    /**
     * 获得通知总数
     * @return
     */
    @Override
    public Integer getAllNewsCount() {
        return wxNewsMapper.selectCount(null);
    }

    /**
     * 通过 newsIdList数组 批量删除学校通知
     * @param newsIdList
     */
    @Override
    public void deleteNews(List<Integer> newsIdList) {
        wxNewsMapper.deleteNews(newsIdList);
    }
}
