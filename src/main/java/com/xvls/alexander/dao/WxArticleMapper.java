package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxArticleMapper extends BaseMapper<Article> {

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

    /**
     * 根据article获取动态信息
     * @param articleId
     * @return
     */
    Article getArticleById(int articleId);

    /**
     * 根据学校Id，获得学校的动态信息
     * @param schoolId
     * @return
     */
    List<Article> getArticleBySchoolId(Integer schoolId);
}
