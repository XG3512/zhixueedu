package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Good;

/**
 * 微信点赞功能
 */
public interface WxToolBarService {
    /*点赞*/
    void addArticleGoodNum(Good good);
    void addVideoGoodNum(Good good);
    void addNoticeGoodNum(Good good);
    void decreaseArticleGoodNum(Good good);
    void decreaseVideoGoodNum(Good good);
    void decreaseNoticeGoodNum(Good good);

    /*评论*/
    void addArticleCommentNum(Good good);
    void addVideoCommentNum(Good good);
    void decreaseArticleCommentNum(Good good);
    void decreaseVideoCommentNum(Good good);

    /*阅读量*/
    void addArticleReadNum(Integer id);
    void addVideoPlayNum(Integer id);
    void addNoticeReadNum(Integer id);


    boolean deleteGood(Good good);
}
