package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Follow_school;
import com.xvls.alexander.entity.wx.Follow_teacher;
import com.xvls.alexander.entity.wx.Good;

/**
 * 微信点赞功能
 */
public interface WxToolBarService {
    /*点赞*/
    Object addArticleGoodNum(Good good);
    Object addVideoGoodNum(Good good);
    Object addNoticeGoodNum(Good good);
    Object decreaseArticleGoodNum(Good good);
    Object decreaseVideoGoodNum(Good good);
    Object decreaseNoticeGoodNum(Good good);

    /*评论*/
    void addArticleCommentNum(Good good);
    void addVideoCommentNum(Good good);
    void decreaseArticleCommentNum(Good good);
    void decreaseVideoCommentNum(Good good);

    /*阅读量*/
    void addArticleReadNum(Integer id);
    void addVideoPlayNum(Integer id);
    void addNoticeReadNum(Integer id);

    /*收藏*/
    void addArticleCollectionNum(Integer id);
    void decreaseArticleCollectionNum(Integer id);
    void addVideoCollectionNum(Integer id);
    void decreaseVideoCollectionNum(Integer id);

    boolean deleteGood(Good good);

    /*关注*/
    void addFollowSchool(Follow_school follow_school);
    void cancelFollowSchool(Follow_school follow_school);
    void addFollowTeacher(Follow_teacher follow_teacher);
    void cancelFollowTeacher(Follow_teacher follow_teacher);

}
