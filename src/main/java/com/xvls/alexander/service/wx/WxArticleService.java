package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import org.springframework.stereotype.Service;

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
}
