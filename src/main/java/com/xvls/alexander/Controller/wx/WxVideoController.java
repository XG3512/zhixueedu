package com.xvls.alexander.Controller.wx;

import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.wx.WxVideoService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/wx/video")
public class WxVideoController {

    @Autowired
    WxVideoService wxVideoService;
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
     * 点开视频之后，进入视频主页面内的信息
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
        Video_main videoMainInfo = wxVideoService.getVideoMainInfo(videoMainId,wxUserId);
        wxVideoService.updateVideoHeatOfVideo(videoMainInfo);
        /**
         * 加入推荐列表信息
         */
        return WeChatResponseUtil.ok(videoMainInfo);
    }
}
