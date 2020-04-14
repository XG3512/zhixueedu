package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Follow_school;
import com.xvls.alexander.entity.wx.Follow_teacher;
import com.xvls.alexander.entity.wx.Good;
import com.xvls.alexander.entity.wx.SchoolList;

import java.util.List;

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
    /**
     * 通过 wxUserId 获得用户关注学校数量
     * @param wxUserId
     * @return
     */
    Integer getFollowSchoolCount(Integer wxUserId);

    /****************************后台管理端*****************************/
    /**
     * 通过 belongType,belongIdList 批量删除收藏记录
     * @param belongType
     * @param belongIdList
     */
    void deleteCollections(String belongType,List<Integer> belongIdList);
    /**
     * 通过 belongType，belongIdList 批量删除点赞记录
     * @param belongType
     * @param belongIdList
     */
    void deleteGoods(String belongType,List<Integer> belongIdList);

}
