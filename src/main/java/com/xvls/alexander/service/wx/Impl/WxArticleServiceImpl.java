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
// TODO: 2020/2/9 点赞、收藏、评论数的字符化，并放入数据库中
@Service
public class WxArticleServiceImpl implements WxArticleService {

    @Autowired
    WxArticleMapper wxArticleMapper;

    @Override
    public List<Article> getArticleByPage(PageInfo pageInfo,Integer wxUserId) {
        return wxArticleMapper.getArticleByPage(pageInfo,wxUserId);
    }

    @Override
    public List<Article> getAllArticle() {
        return wxArticleMapper.getAllArticle();
    }

    @Override
    public Article getArticleById(Integer articleId , Integer wxUserId) {
        return wxArticleMapper.getArticleById(articleId,wxUserId);
    }

    @Override
    public List<Article> getArticleBySchoolId(Integer schoolId,Integer wxUserId) {
        return wxArticleMapper.getArticleBySchoolId(schoolId,wxUserId);
    }

    /**
     * 更新点赞数据
     * @param articleId
     */
    @Override
    public void addArticleGoodNum(Integer articleId) {
        wxArticleMapper.addArticleGoodNum(articleId);
    }

    /**
     * 增加评论数
     * @param articleId
     */
    @Override
    public void addArticleCommentNum(Integer articleId) {
        wxArticleMapper.addArticleCommentNum(articleId);
    }

    /**
     * 增加阅读量
     * @param articleId
     */
    @Override
    public void addArticleReadNum(Integer articleId) {
        wxArticleMapper.addArticleReadNum(articleId);
    }

    /**
     * 减少点赞数
     * @param articleId
     */
    @Override
    public void decreaseArticleGoodNum(Integer articleId) {
        wxArticleMapper.decreaseArticleGoodNum(articleId);
    }

    /**
     * 减少评论数
     * @param articleId
     */
    @Override
    public void decreaseArticleCommentNum(Integer articleId) {
        wxArticleMapper.decreaseArticleCommentNum(articleId);
    }

    /**
     * 添加收藏数
     * @param articleId
     */
    @Override
    public void addArticleCollectionNum(Integer articleId) {
        wxArticleMapper.addArticleCollectionNum(articleId);
    }

    /**
     * 减少收藏数
     * @param articleId
     */
    @Override
    public void decreaseArticleCollectionNum(Integer articleId){
        wxArticleMapper.decreaseArticleCollectionNum(articleId);
    }


}
