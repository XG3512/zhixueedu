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

    List<Article> getArticleByPage(PageInfo pageInfo);

    List<Article> getAllArticle();

    Article getArticleById(int articleId);

    List<Article> getArticleBySchoolId(Integer schoolId);

    void addArticleGoodNum(Integer articleId);

    void addArticleCommentNum(Integer articleId);

    void addArticleReadNum(Integer articleId);

    void decreaseArticleGoodNum(Integer articleId);

    void decreaseArticleCommentNum(Integer articleId);
}
