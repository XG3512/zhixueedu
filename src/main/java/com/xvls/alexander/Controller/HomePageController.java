package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Article;
import com.xvls.alexander.service.SysLogService;
import com.xvls.alexander.service.UsersService;
import com.xvls.alexander.service.Video_mainService;
import com.xvls.alexander.service.wx.*;
import com.xvls.alexander.utils.SystemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/homePage")
public class HomePageController {

    @Autowired
    UsersService usersService;
    @Autowired
    WxArticleService wxArticleService;
    @Autowired
    Video_mainService video_mainService;
    @Autowired
    WxNewsService wxNewsService;
    @Autowired
    WxNoticeService wxNoticeService;
    @Autowired
    WxCommentsService wxCommentsService;
    @Autowired
    WxSchoolService wxSchoolService;
    @Autowired
    SysLogService sysLogService;

    /**
     * 获得首页数据
     * @return
     */
    @RequestMapping("getHomePageData")
    public Object getHomePageData(){

        Integer allArticleNum = wxArticleService.getAllArticleNum();
        Integer videoOfPassCount = video_mainService.getAllVideoCountByVerifyStatus("通过");
        Integer videoOfVerifyCount = video_mainService.getAllVideoCountByVerifyStatus("审核中");
        Integer videoOfNotPassCount = video_mainService.getAllVideoCountByVerifyStatus("未通过");
        Integer usersCount = usersService.getUsersCount();
        Integer allNewsCount = wxNewsService.getAllNewsCount();
        Integer allNoticeCount = wxNoticeService.getAllNoticeCount();
        Integer allCommentNum = wxCommentsService.getAllCommentNum();
        Integer allSchoolCount = wxSchoolService.getAllSchoolCount();
        Integer sysLogCount = sysLogService.getSysLogCount();

        List<Article> articleList = wxArticleService.getSystemHomePageArticle(new PageInfo(1, 6));

        Map result = Maps.newHashMap();
        result.put("allArticleNum",allArticleNum);
        result.put("videoOfPassCount",videoOfPassCount);
        result.put("videoOfVerifyCount",videoOfVerifyCount);
        result.put("videoOfNotPassCount",videoOfNotPassCount);
        result.put("usersCount",usersCount);
        result.put("allNewsCount",allNewsCount);
        result.put("allNoticeCount",allNoticeCount);
        result.put("allCommentNum",allCommentNum);
        result.put("allSchoolCount",allSchoolCount);
        result.put("sysLogCount",sysLogCount);
        result.put("articleList",articleList);
        return SystemResponse.ok(result);
    }
}
