package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.WxNews;
import com.xvls.alexander.service.wx.WxNewsService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 教务通知
 */
@RestController
@RequestMapping("/wx/news")
public class WxNewsController {

    @Autowired
    WxNewsService wxNewsService;

    /**
     * 通过 userId 获得教务通知
     * @param userId
     * @return
     */
    @RequestMapping("getNewsListById")
    public Object getNewsListById(@RequestParam("userId")Integer userId){
        if(userId==null){
            return WeChatResponseUtil.badArgument();
        }
        List<WxNews> wxNewsList = wxNewsService.getNewsListById(userId);
        Map result = Maps.newHashMap();
        result.put("wxNewsList",wxNewsList);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过 newsId 获得教务通知详情信息
     * @param newsId
     * @return
     */
    @RequestMapping("getNewsInfoById")
    public Object getNewsInfoById(@RequestParam("newsId") Integer newsId){
        if(newsId == null){
            return WeChatResponseUtil.badArgument();
        }
        WxNews newsInfo = wxNewsService.getNewsInfoById(newsId);
        Map result = Maps.newHashMap();
        result.put("newsInfo",newsInfo);
        return WeChatResponseUtil.ok(result);
    }
}
