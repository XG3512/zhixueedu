package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.entity.wx.WxDepartment;
import com.xvls.alexander.service.wx.WxSystemVideoService;
import com.xvls.alexander.service.wx.WxVideoService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/systemVideo")
public class WxSystemVideoController {

    @Autowired
    WxSystemVideoService wxSystemVideoService;
    @Autowired
    WxVideoService wxVideoService;

    /**
     * 通过 userId 获得学院及教师列表
     * @param userId
     * @return
     */
    @RequestMapping("getDepartmentListById")
    public Object getDepartmentListById(@RequestParam("userId") Integer userId){

        if(userId == null){
            return WeChatResponseUtil.badArgument();
        }

        List<WxDepartment> departmentList = wxSystemVideoService.getDepartmentListById(userId);

        Map result = Maps.newHashMap();
        result.put("departmentList",departmentList);

        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过 wxUserId,teacherId,pageInfo 得到校内、校外的视频列表
     * @param body
     * @return
     */
    @RequestMapping("getTeacherVideoListByTeacherId")
    public Object getTeacherVideoListByTeacherId(@RequestBody String body){
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
}
