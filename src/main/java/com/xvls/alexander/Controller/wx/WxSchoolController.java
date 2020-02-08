package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.School;
import com.xvls.alexander.entity.wx.Article;
import com.xvls.alexander.entity.wx.Notice;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.wx.WxArticleService;
import com.xvls.alexander.service.wx.WxNoticeService;
import com.xvls.alexander.service.wx.WxSchoolService;
import com.xvls.alexander.service.wx.WxVideoService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 学校主页
 */
@RestController
@RequestMapping("/wx/school")
public class WxSchoolController {

    @Autowired
    WxSchoolService wxSchoolService;
    @Autowired
    WxArticleService wxArticleService;
    @Autowired
    WxNoticeService wxNoticeService;
    @Autowired
    WxVideoService wxVideoService;

    /**
     * 所需数据：schoolId
     * 根据schoolId获取学校主页信息
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("getSchoolMainPage")
    public Object getSchoolMainPage(@RequestParam Integer schoolId , HttpServletRequest httpServletRequest){
        System.out.println(schoolId);
        if(schoolId==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        Map map = Maps.newHashMap();
        School schoolInfo = wxSchoolService.getSchoolInfo(schoolId);
        map.put("schoolInfo",schoolInfo);
        //System.out.println(schoolInfo);
        List<Article> articleList = wxArticleService.getArticleBySchoolId(schoolId);
        map.put("articleList",articleList);
        //System.out.println(articleList);
        List<Notice> noticeList = wxNoticeService.getNoticeListBySchoolId(schoolId);
        map.put("noticeList",noticeList);
        //System.out.println(articleList);
        List<Video_main> video_mainList = wxVideoService.getPublicVideoListBySchoolId(schoolId);
        map.put("video_mainList",video_mainList);
        //System.out.println(video_mainList);
        return WeChatResponseUtil.ok(map);
    }
}
