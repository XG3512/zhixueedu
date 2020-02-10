package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxGoodMapper;
import com.xvls.alexander.entity.wx.Good;
import com.xvls.alexander.service.wx.WxArticleService;
import com.xvls.alexander.service.wx.WxNoticeService;
import com.xvls.alexander.service.wx.WxToolBarService;
import com.xvls.alexander.service.wx.WxVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxToolBarServiceImpl implements WxToolBarService {
    @Autowired
    WxGoodMapper wxGoodMapper;
    @Autowired
    WxArticleService wxArticleService;
    @Autowired
    WxVideoService wxVideoService;
    @Autowired
    WxNoticeService wxNoticeService;

    /**
     * 对动态进行点赞 , 在前端进行判断是否已经点赞
     * @param good
     */
    @Override
    public void addArticleGoodNum(Good good) {
        good.insert();
        wxArticleService.addArticleGoodNum(good.getGoodId());
    }

    /**
     * 对视频进行点赞
     * @param good
     */
    @Override
    public void addVideoGoodNum(Good good) {
        good.insert();
        wxVideoService.addVideoGoodNum(good.getGoodId());
    }

    /**
     * 公告点赞数增加
     * @param good
     */
    @Override
    public void addNoticeGoodNum(Good good) {
        good.insert();
        wxNoticeService.addNoticeGoodNum(good.getGoodId());
    }

    /**
     * 动态取消点赞 , 在前端进行判断是否已经点赞
     * @param good
     */
    @Override
    public void decreaseArticleGoodNum(Good good) {
        wxArticleService.decreaseArticleGoodNum(good.getGoodId());
        boolean delete = this.deleteGood(good);
    }

    /**
     * 视频点赞数减少
     * @param good
     */
    @Override
    public void decreaseVideoGoodNum(Good good) {
        wxVideoService.decreaseVideoGoodNum(good.getGoodId());
        boolean delete = this.deleteGood(good);
    }

    /**
     * 通知点赞数减少
     * @param good
     */
    @Override
    public void decreaseNoticeGoodNum(Good good) {
        wxNoticeService.decreaseNoticeGoodNum(good.getGoodId());
        boolean delete = this.deleteGood(good);
    }

    /**
     * 动态评论数增加
     * @param good
     */
    @Override
    public void addArticleCommentNum(Good good) {
        wxArticleService.addArticleCommentNum(good.getGoodId());
    }

    /**
     * 视频评论数增加
     * @param good
     */
    @Override
    public void addVideoCommentNum(Good good) {
        wxVideoService.addVideoCommentNum(good.getGoodId());
    }

    /**
     * 动态评论数减少
     * @param good
     */
    @Override
    public void decreaseArticleCommentNum(Good good) {
        wxArticleService.decreaseArticleCommentNum(good.getGoodId());
    }

    /**
     * 视频评论数减少
     * @param good
     */
    @Override
    public void decreaseVideoCommentNum(Good good) {
        wxVideoService.decreaseVideoCommentNum(good.getGoodId());
    }

    /**
     * 动态阅读量增加
     * @param id
     */
    @Override
    public void addArticleReadNum(Integer id) {
        wxArticleService.addArticleReadNum(id);
    }

    /**
     * 视频阅读量增加
     * @param id
     */
    @Override
    public void addVideoPlayNum(Integer id) {
        wxVideoService.addVideoPlayNum(id);
    }

    /**
     * 通知阅读量增加
     * @param id
     */
    @Override
    public void addNoticeReadNum(Integer id) {
        wxNoticeService.addNoticeReadNum(id);
    }

    @Override
    public boolean deleteGood(Good good) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_type",good.getGoodType())
                .eq("good_id",good.getGoodId())
                .eq("user_id",good.getUserId());
        return good.delete(queryWrapper);
    }

}
