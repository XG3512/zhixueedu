package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.WxVideoRotation;
import com.xvls.alexander.service.wx.WxVideoRotationService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/videoMainPage")
public class WxVideoMainPageController {

    @Autowired
    WxVideoRotationService wxVideoRotationService;


    /**
     * 获得 视频精选首页 轮播图
     * @return
     */
    @RequestMapping("getVideoRotation")
    public Object getVideoRotation(){

        List<WxVideoRotation> videoRotation = wxVideoRotationService.getVideoRotation();

        Map result = Maps.newHashMap();

        result.put("videoRotation",videoRotation);

        return WeChatResponseUtil.ok(result);
    }
}
