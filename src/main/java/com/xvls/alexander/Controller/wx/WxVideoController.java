package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Label;
import com.xvls.alexander.entity.wx.V_history;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.wx.WxV_historyService;
import com.xvls.alexander.service.wx.WxVideoService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/video")
public class WxVideoController {

    @Autowired
    WxVideoService wxVideoService;
    @Autowired
    WxV_historyService wxV_historyService;
    /**
     * 获得视频列表信息
     * @return
     */
    // TODO: 2020/2/15 视频的分页功能 视频的分类功能
    @RequestMapping("getPublicVideoList")
    public Object getPublicVideoList(@RequestParam("wxUserId") Integer wxUserId){
        if(wxUserId==null){
            return WeChatResponseUtil.badArgument();
        }
        List<Video_main> publicVideoList = wxVideoService.getPublicVideoList(wxUserId);
        return WeChatResponseUtil.ok(publicVideoList);
    }

    /**
     * 根据videoId，获得视频 播放 的详细信息
     * @param videoMainId
     * @param episodeId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("getVideoInfoById")
    public Object getVideoInfoById(@RequestParam("videoMainId") Integer videoMainId,
                                   @RequestParam("episodeId")Integer episodeId,
                                   HttpServletRequest httpServletRequest){
        System.out.println("videoId:"+videoMainId+" episodeId:"+episodeId);
        if(videoMainId==null||episodeId==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        Video videoInfoById = wxVideoService.getVideoInfoById(videoMainId, episodeId);
        return WeChatResponseUtil.ok(videoInfoById);
    }

    /**
     * 点开视频之后，进入视频主页面内的信息,加入了标签列表信息
     * （暂时还没加推荐列表）
     * @param videoMainId
     * @return
     */
    @RequestMapping("getMainPageById")
    public Object getMainPageById(@RequestParam("videoMainId")Integer videoMainId,
                                  @RequestParam("wxUserId") Integer wxUserId){
        if(videoMainId==null||wxUserId==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        Map result = Maps.newHashMap();
        Video_main videoMainInfo = wxVideoService.getVideoMainInfo(videoMainId,wxUserId);
        result.put("videoMainInfo",videoMainInfo);
        wxVideoService.updateVideoHeatOfVideo(videoMainInfo);
        /**
         * 获得历史记录
         */
        V_history v_history = wxV_historyService.getV_historyById(videoMainId, wxUserId);
        result.put("v_history",v_history);
        /**
         * 加入推荐列表信息
         */
        PageInfo pageInfo = new PageInfo(1,6);
        List<Video_main> recommendVideoList = wxVideoService.getPublicVideoListByLabel(pageInfo, videoMainInfo.getLabelList().get(0).getLabelId(), wxUserId);
        result.put("recommendVideoList",recommendVideoList);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过 分页,标签,wxUserId 获得视频列表
     * @return
     */
    @RequestMapping("getPublicVideoListByLabel")
    public Object getPublicVideoListByLabel(@RequestBody String body){
        if(body==null){
            return WeChatResponseUtil.badArgument();
        }
        PageInfo pageInfo = null;
        Integer labelId = null;
        Integer wxUserId = null;
        try {
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            labelId = JacksonUtil.parseInteger(body,"labelId");
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
            if(pageInfo == null || labelId == null || wxUserId == null){
                WeChatResponseUtil.badArgument();
            }
        } catch (Exception e) {
            return WeChatResponseUtil.fail(-1,e.getMessage());
        }
        List<Video_main> video_mainList = wxVideoService.getPublicVideoListByLabel(pageInfo, labelId , wxUserId);
        Map map = Maps.newHashMap();
        map.put("video_mainList",video_mainList);
        return WeChatResponseUtil.ok(map);
    }

    /**
     * 在退出视频时，添加用户观看的历史记录
     * @param v_history
     * @return
     */
    @RequestMapping("addV_history")
    public Object addV_history(@RequestBody V_history v_history){
        System.out.println(v_history);
        if(v_history==null || v_history.getVideoMainId()==null || v_history.getWxUserId()==null || v_history.getEpisodeId()==null || v_history.getWatchTo()==null){
            return WeChatResponseUtil.badArgument();
        }
        if(v_history.getWatchDate()==null){
            v_history.setWatchDate(new Date());
        }
        wxV_historyService.addV_history(v_history);
        return WeChatResponseUtil.ok();
    }
}
