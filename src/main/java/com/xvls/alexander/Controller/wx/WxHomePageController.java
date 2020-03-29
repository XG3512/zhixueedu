package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.entity.wx.WxHomeRotation;
import com.xvls.alexander.service.wx.WxHomeRotationService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/wx/homePage")
public class WxHomePageController {

    @Autowired
    WxHomeRotationService wxHomeRotationService;

    /**
     * 获得轮播图信息
     * @return
     */
    @RequestMapping("getAllHomeRotation")
    public Object getAllHomeRotation(){
        List<WxHomeRotation> homeRotationList = wxHomeRotationService.getAllHomeRotation();
        Map map = Maps.newHashMap();
        map.put("homeRotationList",homeRotationList);
        return WeChatResponseUtil.ok(map);
    }

    /**
     * 对轮播图进行更新
     * @param body
     * @return
     */
    @RequestMapping("/updateHomeRotation")
    public Object updateHomeRotation(@RequestBody String body){
        WxHomeRotation wxHomeRotation = null;
        try {
            wxHomeRotation = JacksonUtil.parseObject(body, WxHomeRotation.class);
            if(wxHomeRotation == null){
                return WeChatResponseUtil.badArgument();
            }
        }catch (Exception e){
            e.printStackTrace();
            return WeChatResponseUtil.badArgument();
        }
        wxHomeRotationService.updateHomeRotation(wxHomeRotation);
        return WeChatResponseUtil.ok();
    }

    /**
     * 获得首页轮播图信息
     * @param wxUserId
     * @return
     */
    @RequestMapping("/getHomePageVideoList")
    public Object getHomePageVideoList(@RequestParam("wxUserId") Integer wxUserId){
        if(wxUserId==null){
            return WeChatResponseUtil.badArgument();
        }
        Map map = Maps.newHashMap();

        List<Video_main> homePageVideoList = wxHomeRotationService.getHomePageVideoList(wxUserId);

        map.put("homePageVideoList",homePageVideoList);

        return WeChatResponseUtil.ok(homePageVideoList);
    }

}
