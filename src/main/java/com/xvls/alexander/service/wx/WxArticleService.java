package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 动态信息
 */
@Service
public interface WxArticleService {

    List<Article> getArticleByPage(PageInfo pageInfo,Integer wxUserId);

    List<Article> getAllArticle();

    /**
     * 通过 pageInfo，wxUserId 获得用户关注学校的动态
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<Article> getFollowArticleListByPage(Integer wxUserId,PageInfo pageInfo);

    /**
     * 通过 articleId ，wxUserId 获得动态详情
     * @param articleId
     * @param wxUserId
     * @return
     */
    Article getArticleById(Integer articleId , Integer wxUserId);

    /**
     * 通过 schoolId，wxUserId，pageInfo 获得学校动态信息
     * @param schoolId
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<Article> getArticleBySchoolId(Integer schoolId,Integer wxUserId,PageInfo pageInfo);

    void addArticleGoodNum(Integer articleId);

    void addArticleCommentNum(Integer articleId);

    void addArticleReadNum(Integer articleId);

    void decreaseArticleGoodNum(Integer articleId);

    void decreaseArticleCommentNum(Integer articleId);

    void addArticleCollectionNum(Integer articleId);

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
     * @return
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
     * @param wxUserId
     * @return
     */
    List<Article> searchArticleByTitle(String title , Integer wxUserId);

    /**
     * 计算并更新热度
     * @param article
     */
    void updateVideoHeatOfVideo(Article article);

    /*--------------------------------------------------------------------------------------*/
    /*--------------------------------------后台管理端---------------------------------------*/
    /*--------------------------------------------------------------------------------------*/

    /**
     * 增加动态
     * @param article
     */
    Integer addArticle(Article article);

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
     * 通过 articleId 删除动态
     * @param articleId
     */
    void deleteArticleById(Integer articleId);

}
