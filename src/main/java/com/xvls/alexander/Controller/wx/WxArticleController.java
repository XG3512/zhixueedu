package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.dao.WxCommentsMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import com.xvls.alexander.entity.wx.Comments;
import com.xvls.alexander.service.wx.WxArticleService;
import com.xvls.alexander.service.wx.WxCommentsService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 通过页数获取动态信息
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
        List<Comments> comments = wxCommentsService.getComments("A", articleId);
        Map<Object,Object> result = Maps.newHashMap();
        result.put("article",article);
        result.put("comments",comments);
        return WeChatResponseUtil.ok(result);
    }
}
