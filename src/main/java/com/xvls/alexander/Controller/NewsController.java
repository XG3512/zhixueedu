package com.xvls.alexander.Controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.WxNews;
import com.xvls.alexander.service.wx.WxNewsService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xvls
 */
@CrossOrigin
@RestController
@RequestMapping("/system/news")
public class NewsController {

    @Autowired
    WxNewsService wxNewsService;

    /**
     * 通过 userId,pageInfo 获得通知列表
     * @param body
     * @return
     */
    @RequiresPermissions("news:select")
    @RequestMapping("getNewsList")
    public Object getNewsList(@RequestBody String body){
        Integer userId = null;
        PageInfo pageInfo = null;
        try {
            userId = JacksonUtil.parseInteger(body,"userId");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if(userId == null || pageInfo == null || pageInfo.getPageNum()==null || pageInfo.getPageSize()==null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<WxNews> newsList = wxNewsService.getNewsListById(userId, pageInfo);
        Integer newsCount = wxNewsService.getNewsCount(userId);
        Map result = Maps.newHashMap();
        result.put("newsList",newsList);
        result.put("count",newsCount);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 userId，title，pageInfo 进行模糊搜索
     * @param body
     * @return
     */
    @RequiresPermissions("news:select")
    @RequestMapping("searchNews")
    public Object searchNews(@RequestBody String body){
        Integer userId = null;
        String content = null;
        PageInfo pageInfo = null;
        try {
            userId = JacksonUtil.parseInteger(body,"userId");
            content = JacksonUtil.parseString(body,"content");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if(userId == null || content == null || pageInfo == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<WxNews> newsList = wxNewsService.searchNews(userId, content, pageInfo);
        Integer count = wxNewsService.getSearchNewsCount(userId, content);
        Map result = Maps.newHashMap();
        result.put("newsList",newsList);
        result.put("count",count);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 userId 获得通知总数
     * @param body
     * @return
     */
    @RequiresPermissions("news:select")
    @RequestMapping("getNewsCount")
    public Object getNewsCount(@RequestBody String body){
        Integer userId = null;
        try {
            userId = JacksonUtil.parseInteger(body,"userId");
            if(userId == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        Integer newsCount = wxNewsService.getNewsCount(userId);
        Map result = Maps.newHashMap();
        result.put("newsCount",newsCount);
        return SystemResponse.ok(result);
    }

    /**
     * 添加通知
     * @param body
     * @return
     * @throws ParseException
     */
    @RequiresPermissions("news:add")
    @RequestMapping("addNews")
    public Object addNews(@RequestBody String body) throws ParseException {
        Integer schoolId = null;//学校id
        String newsDate = null;//通知日期
        String title = null;//通知标题
        String newsContent = null;//通知富文本内容
        String newsText = null;//通知纯文字信息
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            schoolId = JacksonUtil.parseInteger(body,"schoolId");
            newsDate = JacksonUtil.parseString(body,"newsDate");
            title = JacksonUtil.parseString(body,"title");
            newsContent = JacksonUtil.parseString(body,"newsContent");
            newsText = JacksonUtil.parseString(body,"newsText");
            if(schoolId == null || title == null || newsContent == null || newsText == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        WxNews wxNews = new WxNews();
        wxNews.setSchoolId(schoolId);
        if(newsDate == null){
            wxNews.setNewsDate(new Date());
        }else {
            wxNews.setNewsDate(dateFormat.parse(newsDate));
        }
        wxNews.setTitle(title);
        wxNews.setNewsContent(newsContent);
        wxNews.setNewsText(newsText);
        wxNews.setReadNum(0);

        Integer newsId = wxNewsService.addNews(wxNews);
        Map result = Maps.newHashMap();
        result.put("newsId",newsId);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 newsIdList数组 批量删除学校通知
     * @param body
     * @return
     */
    @RequiresPermissions("news:delete")
    @RequestMapping("deleteNews")
    public Object deleteNews(@RequestBody String body){
        List<Integer> newsIdList = null;
        try {
            newsIdList = JacksonUtil.parseIntegerList(body,"newsIdList");
            if(newsIdList==null || newsIdList.size() == 0){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        wxNewsService.deleteNews(newsIdList);
        return SystemResponse.ok();
    }


}
