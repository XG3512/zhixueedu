package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxArticleMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import com.xvls.alexander.service.wx.WxArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态信息
 */
@Service
public class WxArticleServiceImpl implements WxArticleService {

    @Autowired
    WxArticleMapper wxArticleMapper;

    @Override
    public List<Article> getArticleByPage(PageInfo pageInfo) {
        return wxArticleMapper.getArticleByPage(pageInfo);
    }

    @Override
    public List<Article> getAllArticle() {
        return wxArticleMapper.getAllArticle();
    }

    @Override
    public Article getArticleById(int articleId) {
        return wxArticleMapper.getArticleById(articleId);
    }

    @Override
    public List<Article> getArticleBySchoolId(Integer schoolId) {
        return wxArticleMapper.getArticleBySchoolId(schoolId);
    }
}
