package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.annotation.SysLog;
import com.xvls.alexander.entity.wx.WxVideoRotation;
import com.xvls.alexander.service.wx.WxVideoRotationService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/videoRotation")
public class VideoRotationController {

    @Autowired
    WxVideoRotationService wxVideoRotationService;

    /**
     * 获得视频轮播图信息
     * @return
     */
    @RequiresPermissions("video_rotation:select")
    @RequestMapping("getVideoRotationInfo")
    public Object getVideoRotationInfo(){
        List<WxVideoRotation> videoRotation = wxVideoRotationService.getVideoRotation();
        Map result = Maps.newHashMap();
        result.put("videoRotation",videoRotation);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 videoRotationId，videoMainId 修改视频信息
     * @param body
     * @return
     */
    @RequiresPermissions("video_rotation:update")
    @RequestMapping("updateVideoRotation")
    public Object updateVideoRotation(@RequestBody String body){
        Integer videoRotationId = null;
        Integer videoMainId = null;
        try {
            videoRotationId = JacksonUtil.parseInteger(body,"videoRotationId");
            videoMainId = JacksonUtil.parseInteger(body,"videoMainId");
            if(videoRotationId == null || videoMainId == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        wxVideoRotationService.updateVideoRotation(videoRotationId,videoMainId);
        return SystemResponse.ok();
    }
}
