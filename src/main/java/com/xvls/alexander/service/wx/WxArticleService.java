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
}
