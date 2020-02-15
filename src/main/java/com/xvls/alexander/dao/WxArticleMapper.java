package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxArticleMapper extends BaseMapper<Article> {

    /**
     * 通过 分页信息,wxUserId 获取动态信息
     * @param pageInfo
     * @return
     */
    List<Article> getArticleByPage(PageInfo pageInfo,Integer wxUserId);

    /**
     * 获取所有动态
     * @return
     */
    // TODO: 2020/2/13 可以用来改成首页的热门动态
    List<Article> getAllArticle();

    /**
     * 根据articleId , wxUserId 获取动态信息
     * @param articleId
     * @return
     */
    Article getArticleById(Integer articleId,Integer wxUserId);

    /**
     * 根据学校Id，获得学校的动态信息
     * @param schoolId
     * @return
     */
    List<Article> getArticleBySchoolId(Integer schoolId,Integer wxUserId);

    /**
     * 增加点赞
     * @param articleId
     */
    void addArticleGoodNum(Integer articleId);

    /**
     * 增加评论数
     * @param articleId
     */
    void addArticleCommentNum(Integer articleId);

    /**
     * 增加阅读量
     * @param articleId
     */
    void addArticleReadNum(Integer articleId);

    /**
     * 减少点赞数
     * @param articleId
     */
    void decreaseArticleGoodNum(Integer articleId);

    /**
     * 减少评论数
     * @param articleId
     */
    void decreaseArticleCommentNum(Integer articleId);

    /**
     * 增加收藏数
     * @param articleId
     */
    void addArticleCollectionNum(Integer articleId);

    /**
     * 减少收藏数
     * @param articleId
     */
    void decreaseArticleCollectionNum(Integer articleId);
}
