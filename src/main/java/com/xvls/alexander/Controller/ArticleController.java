package com.xvls.alexander.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xvls.alexander.annotation.SysLog;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import com.xvls.alexander.service.FileCrudService;
import com.xvls.alexander.service.QiniuService;
import com.xvls.alexander.service.wx.WxArticleService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台管理端 动态管理
 */
@CrossOrigin
@RestController
@RequestMapping("/system/article")
public class ArticleController {

    @Autowired
    WxArticleService wxArticleService;
    @Autowired
    QiniuService qiniuService;
    @Autowired
    FileCrudService fileCrudService;

    /**
     * 添加 动态
     * @param body
     * @return
     */
    @RequiresPermissions("article:add")
    @RequestMapping("addArticle")
    @SysLog("添加 动态")
    public Object addArticle(@RequestBody String body){

        Integer schoolId = null;
        String title = null;
        String content = null;
        String articleText = null;
        Boolean commentStatus = false;
        Integer userId = null;
        List<String> fileHashList = null;
        List<String> nameList = null;

        try {
            schoolId = JacksonUtil.parseInteger(body, "schoolId");
            title = JacksonUtil.parseString(body,"title");
            content = JacksonUtil.parseString(body,"content");
            articleText = JacksonUtil.parseString(body,"articleText");
            commentStatus = JacksonUtil.parseBoolean(body,"commentStatus");
            userId = JacksonUtil.parseInteger(body,"userId");
            fileHashList = JacksonUtil.parseStringList(body,"fileHashList");
            nameList = JacksonUtil.parseStringList(body,"nameList");

            if(schoolId == null || title == null || content == null || articleText == null || commentStatus == null || userId==null || fileHashList == null || nameList == null){
                return SystemResponse.badArgument();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"参数错误:"+e.getMessage());
        }

        Article article = new Article();
        article.setSchoolId(schoolId);
        article.setTitle(title);
        article.setContent(content);
        article.setArticleText(articleText);
        article.setArticleTime(new Date());
        article.setCommentStatus(commentStatus);
        article.setReadNum(0);
        article.setGoodNum(0);
        article.setCommentNum(0);
        article.setCollectionNum(0);

        Integer articelId = wxArticleService.addArticle(article);

        /**将 file_belong 信息导入*/
        if(fileHashList.size()==nameList.size() && fileHashList.size()>0 && nameList.size()>0){
            List<File_belong> file_belongs = Lists.newArrayList();
            for(int i=0; i<fileHashList.size();i++){
                File_belong file_belong = new File_belong();
                file_belong.setUserId(userId);
                file_belong.setBelongType("A");
                file_belong.setBelongId(articelId);
                file_belong.setFileHash(fileHashList.get(i));
                file_belong.setName(nameList.get(i));
                file_belongs.add(file_belong);
            }
            fileCrudService.insertFileBelong(file_belongs);
        }

        Map result = Maps.newHashMap();
        result.put("articelId",articelId);

        return SystemResponse.ok(result);
    }

    /**
     * 通过 userId和pageInfo 获得动态列表
     * @param body
     * @return
     */
    @RequiresPermissions("article:select")
    @RequestMapping("getArticleListPage")
    public Object getArticleListPage(@RequestBody String body){
        PageInfo pageInfo = null;
        Integer userId = null;

        try {
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            userId = JacksonUtil.parseInteger(body,"userId");
            if(pageInfo==null || userId==null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.badArgument();
        }
        List<Article> articleList = wxArticleService.getSystemArticleList(pageInfo, userId);
        Integer articleNum = wxArticleService.getArticleNumByUserId(userId);
        Map result = Maps.newHashMap();
        result.put("articleList",articleList);
        result.put("articleNum",articleNum);

        return SystemResponse.ok(result);
    }

    /**
     * 通过 userId，content，pageInfo 模糊查询动态
     * @param body
     * @return
     */
    @RequiresPermissions("article:select")
    @RequestMapping("searchArticle")
    public Object searchArticle(@RequestBody String body){
        PageInfo pageInfo = null;
        Integer userId = null;
        String content = null;

        try {
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            userId = JacksonUtil.parseInteger(body,"userId");
            content = JacksonUtil.parseString(body,"content");
            if(pageInfo==null || userId==null || content == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.badArgument();
        }
        List<Article> articleList = wxArticleService.searchArticle(userId, content, pageInfo);
        Integer articleNum = wxArticleService.getSearchArticleCount(userId,content);
        Map result = Maps.newHashMap();
        result.put("articleList",articleList);
        result.put("articleNum",articleNum);

        return SystemResponse.ok(result);
    }

    /**
     * 通过 articleId，userId，commentStatus 更新评论状态
     * @param articleId
     * @param commentStatus
     * @return
     */
    @RequiresPermissions("article:updateCommentStatus")
    @RequestMapping("setArticleCommentStatus")
    @SysLog("通过 articleId，userId，commentStatus 更新评论状态")
    public Object setArticleCommentStatus(@RequestParam("articleId") Integer articleId,
                                          @RequestParam("commentStatus")Boolean commentStatus){
        if(articleId == null || commentStatus == null){
            return SystemResponse.badArgument();
        }
        wxArticleService.updateArticleCommentStatus(articleId,commentStatus);
        return SystemResponse.ok();
    }

    /**
     * 通过 articleId 删除动态及file_belong
     * @param body
     * @return
     */
    @RequiresPermissions("article:delete")
    @RequestMapping("deleteArticleByIdList")
    @SysLog("通过 articleId 删除动态及file_belong")
    public Object deleteArticleByIdList(@RequestBody String body){
        List<Integer> articleIdList = null;
        try {
            articleIdList = JacksonUtil.parseIntegerList(body,"articleIdList");
            if(articleIdList==null || articleIdList.size()==0){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        wxArticleService.deleteArticleByIdList(articleIdList);
        return SystemResponse.ok();
    }
}
