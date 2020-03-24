package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.FileBelongMapper;
import com.xvls.alexander.dao.WxArticleMapper;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.wx.WxArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 动态信息
 */
@Service
public class WxArticleServiceImpl implements WxArticleService {

    @Autowired
    WxArticleMapper wxArticleMapper;
    @Autowired
    FileBelongMapper fileBelongMapper;

    @Override
    public List<Article> getArticleByPage(PageInfo pageInfo,Integer wxUserId) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageInfo.setPageNum((pageNum-1)*pageSize);
        return wxArticleMapper.getArticleByPage(pageInfo,wxUserId);
    }

    @Override
    public List<Article> getAllArticle() {
        return wxArticleMapper.getAllArticle();
    }

    /**
     * 通过 pageInfo，wxUserId 获得用户关注学校的动态
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    @Override
    public List<Article> getFollowArticleListByPage(Integer wxUserId, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum - 1)*pageSize;
        return wxArticleMapper.getFollowArticleListByPage(wxUserId,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 articleId ，wxUserId 获得动态详情
     * @param articleId
     * @param wxUserId
     * @return
     */
    @Override
    public Article getArticleById(Integer articleId , Integer wxUserId) {
        return wxArticleMapper.getArticleById(articleId,wxUserId);
    }

    /**
     * 通过 schoolId，wxUserId，pageInfo 获得学校动态信息
     * @param schoolId
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    @Override
    public List<Article> getArticleBySchoolId(Integer schoolId,Integer wxUserId,PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return wxArticleMapper.getArticleBySchoolId(schoolId,wxUserId,new PageInfo(pageNum,pageSize));
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

    /**
     * 通过 articleId 更新动态评论总数
     * @param articleId
     */
    @Override
    public void updateArticleCommentsNum(Integer articleId) {
        wxArticleMapper.updateArticleCommentsNum(articleId);
    }

    /**
     * 通过 wxUserId 和 pageInfo 获得动态的点赞记录
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    @Override
    public List<Article> getArticleGoodList(Integer wxUserId, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageInfo.setPageNum((pageNum-1)*pageSize);

        return wxArticleMapper.getArticleGoodList(wxUserId,pageInfo);
    }

    /**
     * 通过 wxUserId 和 pageInfo 获得动态的收藏记录
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    @Override
    public List<Article> getArticleCollectionList(Integer wxUserId, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageInfo.setPageNum((pageNum-1)*pageSize);

        return wxArticleMapper.getArticleCollectionList(wxUserId,pageInfo);
    }

    /**
     * 通过 wxUserId ， pageInfo 获得主页动态列表
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    @Override
    public List<Article> getHomePageArticleList(Integer wxUserId, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return wxArticleMapper.getHomePageArticleList(wxUserId,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过动态 title 搜索文章
     * @param title
     * @return
     */
    @Override
    public List<Article> searchArticleByTitle(String title , Integer wxUserId) {
        title = "%"+title+"%";
        return wxArticleMapper.searchArticleByTitle(title,wxUserId);
    }

    @Override
    public void updateVideoHeatOfVideo(Article article) {
        Double result = Calculate(article);
        wxArticleMapper.updateArticleHeatOfArticle(article.getArticleId(),result);
    }


    /*--------------------------------------------------------------------------------------*/
    /*--------------------------------------后台管理端---------------------------------------*/
    /*--------------------------------------------------------------------------------------*/

    /**
     * 增加动态
     * @param article
     */
    @Override
    public Integer addArticle(Article article) {
        Double result = Calculate(article);
        article.setHeatOfArticle(result);
        wxArticleMapper.insert(article);
        return article.getArticleId();
    }

    /**
     * 通过 userId和pageInfo 获得动态列表
     * @param pageInfo
     * @param userId
     * @return
     */
    @Override
    public List<Article> getSystemArticleList(PageInfo pageInfo, Integer userId) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return wxArticleMapper.getSystemArticleList(new PageInfo(pageNum,pageSize),userId);
    }

    /**
     * 通过 userId，title，pageInfo 模糊查询动态
     * @param userId
     * @param content
     * @param pageInfo
     * @return
     */
    @Override
    public List<Article> searchArticle(Integer userId, String content, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        content = "%"+content+"%";
        return wxArticleMapper.searchArticle(userId,content,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 userId，title 获得模糊查询数目
     * @param userId
     * @param content
     * @return
     */
    @Override
    public Integer getSearchArticleCount(Integer userId, String content) {
        content = "%"+content+"%";
        return wxArticleMapper.getSearchArticleCount(userId,content);
    }

    /**
     * 通过 userId 获得动态总数目
     * @param userId
     * @return
     */
    @Override
    public Integer getArticleNumByUserId(Integer userId) {
        return wxArticleMapper.getArticleNumByUserId(userId);
    }

    /**
     * 通过 articleId，userId，commentStatus 更新评论状态
     * @param articleId
     * @param commentStatus
     */
    @Override
    public void updateArticleCommentStatus(Integer articleId, Boolean commentStatus) {
        wxArticleMapper.updateArticleCommentStatus(articleId,commentStatus);
    }

    /**
     * 通过 articleIdList 删除动态 即 相应的 file_belong
     * @param articleIdList
     */
    @Override
    public void deleteArticleByIdList(List<Integer> articleIdList) {
        fileBelongMapper.deleteFileBelongByArticleIdList(articleIdList);
        wxArticleMapper.deleteArticleByIdList(articleIdList);
    }

    public Double Calculate(Article article){
        Double result = null;

        Long articleTime = (article.getArticleTime().getTime()/3600000);
        Double readNum = article.getReadNum().doubleValue();
        Double goodNum = article.getGoodNum().doubleValue();
        Double collectionNum = article.getCollectionNum().doubleValue();
        Double commentNum = article.getCommentNum().doubleValue();
        Long date = new Date().getTime()/3600000;

        result = (readNum*0.0769+commentNum*0.1538+goodNum*0.3077+collectionNum*0.4615)*1000+100.0;//1,2,4,6
        Double temp = Math.pow(date-articleTime+2,1.4);
        //Double temp = Math.pow(Math.E,0.01279*(date-videoDate));
        result = result/temp;
        return result;
    }


}
