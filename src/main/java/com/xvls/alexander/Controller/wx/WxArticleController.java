package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.MsgSecCheckResult;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import com.xvls.alexander.entity.wx.Comments;
import com.xvls.alexander.service.wx.WxArticleService;
import com.xvls.alexander.service.wx.WxCommentsService;
import com.xvls.alexander.utils.ContentSecurityUtil;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态信息
 */
@RestController
@RequestMapping("/wx/article")
@Validated
public class WxArticleController {

    @Autowired
    WxArticleService wxArticleService;
    @Autowired
    WxCommentsService wxCommentsService;
    @Autowired
    ContentSecurityUtil contentSecurityUtil;

    /**
     * 通过 页数 获取全部动态信息
     * @return
     */
    @RequestMapping("getArticleByPage")
    public Object getArticleByPage(@RequestBody String body, HttpServletRequest request){
        PageInfo pageInfo = null;
        Integer wxUserId = null;
        try {
            pageInfo = JacksonUtil.parseObject(body,"pageInfo", PageInfo.class);
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
        } catch (Exception e) {
            e.printStackTrace();
            WeChatResponseUtil.badArgument();
        }
        if(pageInfo==null || wxUserId==null){
            return WeChatResponseUtil.fail();
        }
        List<Article> articles = wxArticleService.getArticleByPage(pageInfo,wxUserId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("articleInfo",articles);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过 pageInfo，wxUserId 获得用户关注学校的动态
     * @param body
     * @return
     */
    @RequestMapping("getFollowArticleListByPage")
    public Object getFollowArticleListByPage(@RequestBody String body){

        System.out.println(body);

        PageInfo pageInfo = null;
        Integer wxUserId = null;

        try {
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return WeChatResponseUtil.badArgument();
        }
        if(pageInfo == null || wxUserId == null){
            return WeChatResponseUtil.badArgumentValue();
        }

        List<Article> followArticleList = wxArticleService.getFollowArticleListByPage(wxUserId, pageInfo);
        Map result = Maps.newHashMap();
        result.put("followArticleList",followArticleList);

        return WeChatResponseUtil.ok(result);
    }

    @RequestMapping("list")
    public Object getAllArticle(){
        List<Article> articles = wxArticleService.getAllArticle();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("articleInfo",articles);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过articleId获取动态详细信息，
     * 评论部分暂时没有设置分页
     * @param articleId
     * @return
     */
    // TODO: 2020/2/9  该部分暂时没有设置评论功能的分页
    @RequestMapping("getArticleById")
    public Object getArticleById(@RequestParam("articleId") Integer articleId,@RequestParam("wxUserId") Integer wxUserId){
        if(articleId == null || wxUserId == null){
            return WeChatResponseUtil.badArgumentValue();
        }
        Article article = wxArticleService.getArticleById(articleId,wxUserId);
        if(article != null){
            wxArticleService.updateVideoHeatOfVideo(article);
        }
        //List<Comments> comments = wxCommentsService.getComments("A", articleId);
        Map<Object,Object> result = Maps.newHashMap();
        result.put("article",article);
        //result.put("comments",comments);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过 wxUserId 获得主页动态列表
     * @param wxUserId
     * @return
     */
    @RequestMapping("getHomePageArticleList")
    public Object getHomePageArticleList(@RequestParam("wxUserId") Integer wxUserId){
        if(wxUserId == null){
            return WeChatResponseUtil.badArgument();
        }
        Map result = Maps.newHashMap();

        List<Article> homePageArticleList = wxArticleService.getHomePageArticleList(wxUserId, new PageInfo(1, 6));

        result.put("homePageArticleList",homePageArticleList);

        return WeChatResponseUtil.ok(result);
    }

    /**
     * 对动态进行评论，如果没有父评论的话parentVcId设置为0
     * @return
     */
    @RequestMapping("addArticleComment")
    public Object addArticleComment(@RequestParam("wxUserId")Integer wxUserId,
                                    @RequestParam("articleId")Integer articleId,
                                    @RequestParam("vcContent")String vcContent,
                                    @RequestParam("parentVcId")Integer parentVcId,
                                    @RequestParam(value = "parentName",required = false) String parentName){
        if(wxUserId==null || articleId==null || vcContent==null || parentVcId==null){
            return WeChatResponseUtil.badArgument();
        }

        HashMap checkResult = (HashMap) contentSecurityUtil.msgSecCheck(vcContent);
        MsgSecCheckResult msgSecCheckResult = (MsgSecCheckResult)checkResult.get("data");
        if(checkResult.get("code").equals(0) && msgSecCheckResult.getErrcode().equals(0)){
            Comments comments = new Comments();
            comments.setWxUserId(wxUserId);
            comments.setBelongId(articleId);
            comments.setBelongType("A");
            comments.setVcContent(vcContent);
            comments.setVcDate(new Date());
            comments.setParentVcId(parentVcId);
            comments.setParentName(parentName);

            /**
             * 增加评论数量
             */
            wxArticleService.addArticleCommentNum(articleId);

            Integer commentId = wxCommentsService.addComment(comments);
            Map result = Maps.newHashMap();
            result.put("commentId",commentId);
            return WeChatResponseUtil.ok(result);
        }else if (checkResult.get("code").equals(0) && msgSecCheckResult.getErrcode().equals(87014)){
            return WeChatResponseUtil.contentIllegal();
        }else{
            return WeChatResponseUtil.fail(-1,"评论失败！");
        }


    }

    /**
     * 通过 schoolId，wxUserId，pageInfo 获得学校动态信息
     * @param body
     * @return
     */
    @RequestMapping("getSchoolArticleList")
    public Object getSchoolArticleList(@RequestBody String body){

        Integer schoolId = null;
        Integer wxUserId = null;
        PageInfo pageInfo = null;

        try {
            schoolId = JacksonUtil.parseInteger(body,"schoolId");
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if(schoolId == null || wxUserId == null || pageInfo == null || pageInfo.getPageNum()==null || pageInfo.getPageSize()==null){
                return WeChatResponseUtil.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return WeChatResponseUtil.badArgument();
        }
        List<Article> articleList = wxArticleService.getArticleBySchoolId(schoolId, wxUserId, pageInfo);
        Map result = Maps.newHashMap();
        result.put("articleList",articleList);

        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过动态 title 搜索文章
     * @param title
     * @param wxUserId
     * @return
     */
    @RequestMapping("searchArticleByTitle")
    public Object searchArticleByTitle(@RequestParam("title") String title , @RequestParam("wxUserId") Integer wxUserId){

        if(title == null || wxUserId == null){
            return WeChatResponseUtil.badArgument();
        }

        List<Article> articles = wxArticleService.searchArticleByTitle(title,wxUserId);
        Map result = Maps.newHashMap();
        result.put("articles",articles);

        return WeChatResponseUtil.ok(result);
    }
}
