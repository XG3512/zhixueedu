package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.TeacherMainPage;
import com.xvls.alexander.service.wx.WxUsersService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 教师主页
 */
@RestController
@RequestMapping("/wx/teacher")
public class WxTeacherController {

    @Autowired
    WxUsersService wxUsersService;

    /**
     * 获得教师主页信息
     * @return
     */
    @RequestMapping("getTeacherMainPage")
    public Object getTeacherMainPage(@RequestParam("teacherId")Integer teacherId,@RequestParam("wxUserId")Integer wxUserId){
        if(teacherId == null || wxUserId == null){
            return WeChatResponseUtil.badArgument();
        }
        TeacherMainPage teacherMainPage = wxUsersService.getTeacherMainPage(wxUserId, teacherId);
        Map map = Maps.newHashMap();
        map.put("teacherMainPage",teacherMainPage);
        return WeChatResponseUtil.ok(map);
    }

    /**
     * 通过 wxUserId 获得关注教师列表
     * @param wxUserId
     * @return
     */
    @RequestMapping("getFollowTeacherList")
    public Object getFollowTeacherList(@RequestParam("wxUserId") Integer wxUserId){

        if(wxUserId==null){
            return WeChatResponseUtil.badArgument();
        }

        List<TeacherMainPage> followTeacherList = wxUsersService.getFollowTeacherList(wxUserId);

        Map result = Maps.newHashMap();
        result.put("followTeacherList",followTeacherList);

        return WeChatResponseUtil.ok(result);
    }
}
