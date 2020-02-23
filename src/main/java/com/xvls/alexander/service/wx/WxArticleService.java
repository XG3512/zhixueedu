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

    Article getArticleById(Integer articleId , Integer wxUserId);

    List<Article> getArticleBySchoolId(Integer schoolId,Integer wxUserId);

    void addArticleGoodNum(Integer articleId);

    void addArticleCommentNum(Integer articleId);

    void addArticleReadNum(Integer articleId);

    void decreaseArticleGoodNum(Integer articleId);

    void decreaseArticleCommentNum(Integer articleId);

    void addArticleCollectionNum(Integer articleId);

    void decreaseArticleCollectionNum(Integer articleId);

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
}
