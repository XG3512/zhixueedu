package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.TeacherMainPage;
import com.xvls.alexander.service.wx.UsersService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 教师主页
 */
@RestController
@RequestMapping("/wx/teacher")
public class WxTeacherController {

    @Autowired
    UsersService usersService;

    /**
     * 获得教师主页信息
     * @return
     */
    @RequestMapping("getTeacherMainPage")
    public Object getTeacherMainPage(@RequestParam("teacherId")Integer teacherId,@RequestParam("wxUserId")Integer wxUserId){
        if(teacherId == null || wxUserId == null){
            return WeChatResponseUtil.badArgument();
        }
        TeacherMainPage teacherMainPage = usersService.getTeacherMainPage(wxUserId, teacherId);
        Map map = Maps.newHashMap();
        map.put("teacherMainPage",teacherMainPage);
        return WeChatResponseUtil.ok(map);
    }
}
