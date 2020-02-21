package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.Class_schedule;
import com.xvls.alexander.service.wx.WxClass_scheduleService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 课表 Controller
 */
@RestController
@RequestMapping("/wx/class_schedule")
public class WxClass_scheduleController {

    @Autowired
    WxClass_scheduleService wxClass_scheduleService;

    /**
     * 通过 userId 查询课表
     * @param userId
     * @return
     */
    @RequestMapping("getClassScheduleById")
    public Object getClassScheduleById(@RequestParam("userId") Integer userId){
        if(userId==null){
            return WeChatResponseUtil.badArgument();
        }
        Map result = Maps.newHashMap();

        List<Class_schedule> scheduleList = wxClass_scheduleService.getClassScheduleById(userId);

        result.put("scheduleList",scheduleList);

        return WeChatResponseUtil.ok(scheduleList);
    }
}
