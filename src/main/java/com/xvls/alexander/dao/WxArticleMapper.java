package com.xvls.alexander.dao;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxArticleMapper {

    /**
     * 通过分页信息，获取动态信息
     * @param pageInfo
     * @return
     */
    List<Article> getArticleByPage(PageInfo pageInfo);

    /**
     * 获取所有动态
     * @return
     */
    List<Article> getAllArticle();
}
