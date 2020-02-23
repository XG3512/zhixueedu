package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.*;
import com.xvls.alexander.service.wx.WxCommentsService;
import com.xvls.alexander.service.wx.WxV_historyService;
import com.xvls.alexander.service.wx.WxVideoRotationService;
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
    @Autowired
    WxCommentsService wxCommentsService;
    @Autowired
    WxVideoRotationService wxVideoRotationService;
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
     * 通过 videoMainId 获得评论信息
     * @param videoMainId
     * @return
     */
    @RequestMapping("getVideo_mainComments")
    public Object getVideo_mainComments(@RequestParam("videoMainId")Integer videoMainId){
        if(videoMainId==null){
            return WeChatResponseUtil.badArgument();
        }
        /**
         * 加入评论
         */
        Map result = Maps.newHashMap();
        List<Comments> comments = wxCommentsService.getComments("V", videoMainId);
        result.put("comments",comments);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 对视频进行评论，如果没有父评论的话parentVcId设置为0
     * @return
     */
    @RequestMapping("addVideoComment")
    public Object addVideoComment(@RequestParam("wxUserId")Integer wxUserId,
                                  @RequestParam("videoMainId")Integer videoMainId,
                                  @RequestParam("vcContent")String vcContent,
                                  @RequestParam("parentVcId")Integer parentVcId){
        if(wxUserId==null || videoMainId==null || vcContent==null || parentVcId==null){
            return WeChatResponseUtil.badArgument();
        }
        Comments comments = new Comments();
        comments.setWxUserId(wxUserId);
        comments.setBelongId(videoMainId);
        comments.setBelongType("V");
        comments.setVcContent(vcContent);
        comments.setVcDate(new Date());
        comments.setParentVcId(parentVcId);

        Integer commentId = wxCommentsService.addComment(comments);
        Map result = Maps.newHashMap();
        result.put("commentId",commentId);
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
        if(v_history==null || v_history.getVideoMainId()==null || v_history.getWxUserId()==null || v_history.getEpisodeId()==null){
            return WeChatResponseUtil.badArgument();
        }
        if(v_history.getWatchDate()==null){
            v_history.setWatchDate(new Date());
        }
        wxV_historyService.addV_history(v_history);
        return WeChatResponseUtil.ok();
    }

    /**
     * 通过 wxUserId、teacherId和pageInfo 获得教师主页的视频列表
     * @param body
     * @return
     */
    @RequestMapping("getPublicVideoListbyTeacherId")
    public Object getPublicVideoListbyTeacherId(@RequestBody String body){
        Integer wxUserId = null;
        Integer teacherId = null;
        PageInfo pageInfo = null;

        try {
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
            teacherId = JacksonUtil.parseInteger(body,"teacherId");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if(wxUserId==null||teacherId==null||pageInfo==null){
                return WeChatResponseUtil.badArgument();
            }
        } catch (Exception e) {
            return WeChatResponseUtil.badArgument();
        }

        List<Video_main> video_mainList = wxVideoService.getPublicVideoListbyTeacherId(wxUserId, teacherId, pageInfo);
        Map map = Maps.newHashMap();
        map.put("video_mainList",video_mainList);

        return WeChatResponseUtil.ok(map);
    }

    /**
     * 通过 wxUserId 和 pageInfo 获得视频历史记录
     * @param body
     * @return
     */
    @RequestMapping("getV_historyByIdAndPage")
    public Object getV_historyByIdAndPage(@RequestBody String body){

        Integer wxUserId = null;
        PageInfo pageInfo = null;

        try {
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
        } catch (Exception e) {
            return WeChatResponseUtil.badArgument();
        }
        if(wxUserId==null || pageInfo==null){
            return WeChatResponseUtil.badArgumentValue();
        }
        Map result = Maps.newHashMap();
        List<WxV_history> v_historyList = wxV_historyService.getV_historyByIdAndPage(wxUserId, pageInfo);
        result.put("v_historyList",v_historyList);

        return WeChatResponseUtil.ok(result);
    }


}
