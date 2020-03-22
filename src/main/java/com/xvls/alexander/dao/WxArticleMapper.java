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
     * 通过 pageInfo，wxUserId 获得用户关注学校的动态
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<Article> getFollowArticleListByPage(Integer wxUserId,PageInfo pageInfo);

    /**
     * 通过 schoolId，wxUserId，pageInfo 获得学校动态信息
     * @param schoolId
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<Article> getArticleBySchoolId(Integer schoolId,Integer wxUserId,PageInfo pageInfo);

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

    /**
     * 通过 articleId 更新动态评论总数
     * @param articleId
     */
    void updateArticleCommentsNum(Integer articleId);

    /**
     * 通过 wxUserId 和 pageInfo 获得动态的点赞记录
     * @param wxUserId
     * @param pageInfo
     */
    List<Article> getArticleGoodList(Integer wxUserId,PageInfo pageInfo);

    /**
     * 通过 wxUserId 和 pageInfo 获得动态的收藏记录
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<Article> getArticleCollectionList(Integer wxUserId,PageInfo pageInfo);

    /**
     * 通过 wxUserId ， pageInfo 获得主页动态列表
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<Article> getHomePageArticleList(Integer wxUserId,PageInfo pageInfo);

    /**
     * 通过动态 title 搜索文章
     * @param title
     * @return
     */
    List<Article> searchArticleByTitle(String title , Integer wxUserId);

    /**
     * 计算并更新热度
     * @param articleId
     * @param heatOfArticle
     */
    void updateArticleHeatOfArticle(Integer articleId , Double heatOfArticle);

    /****************************************************************************************/
    /***************************************后台管理端****************************************/
    /****************************************************************************************/

    /**
     * 通过 userId和pageInfo 获得动态列表
     * @param pageInfo
     * @param userId
     * @return
     */
    List<Article> getSystemArticleList(PageInfo pageInfo,Integer userId);

    /**
     * 通过 userId 获得动态总数目
     * @param userId
     * @return
     */
    Integer getArticleNumByUserId(Integer userId);

    /**
     * 通过 articleId，userId，commentStatus 更新评论状态
     * @param articleId
     * @param commentStatus
     */
    void updateArticleCommentStatus(Integer articleId,Boolean commentStatus);

    /**
     * 通过 articleIdList 批量删除动态
     * @param articleIdList
     */
    void deleteArticleByIdList(List<Integer> articleIdList);
}
