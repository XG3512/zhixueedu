package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.School;
import com.xvls.alexander.entity.wx.*;
import com.xvls.alexander.service.wx.WxArticleService;
import com.xvls.alexander.service.wx.WxNoticeService;
import com.xvls.alexander.service.wx.WxSchoolService;
import com.xvls.alexander.service.wx.WxVideoService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 学校主页
 */
@RestController
@RequestMapping("/wx/school")
public class WxSchoolController {

    @Autowired
    WxSchoolService wxSchoolService;
    @Autowired
    WxArticleService wxArticleService;
    @Autowired
    WxNoticeService wxNoticeService;
    @Autowired
    WxVideoService wxVideoService;

    /**
     * 所需数据：schoolId
     * 根据schoolId获取学校主页信息
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("getSchoolMainPage")
    public Object getSchoolMainPage(@RequestParam("schoolId") Integer schoolId,
                                    @RequestParam("wxUserId") Integer wxUserId ,
                                    HttpServletRequest httpServletRequest){
        System.out.println(schoolId);
        if(schoolId==null || wxUserId == null){
            return WeChatResponseUtil.badArgumentValue();
        }
        Map map = Maps.newHashMap();
        School schoolInfo = wxSchoolService.getSchoolInfo(schoolId,wxUserId);
        map.put("schoolInfo",schoolInfo);
        //System.out.println(schoolInfo);
        List<Article> articleList = wxArticleService.getArticleBySchoolId(schoolId,wxUserId);
        map.put("articleList",articleList);
        //System.out.println(articleList);
        List<Notice> noticeList = wxNoticeService.getNoticeListBySchoolId(schoolId);
        map.put("noticeList",noticeList);
        //System.out.println(articleList);
        List<Video_main> video_mainList = wxVideoService.getPublicVideoListBySchoolId(schoolId,wxUserId);
        map.put("video_mainList",video_mainList);
        //System.out.println(video_mainList);
        return WeChatResponseUtil.ok(map);
    }

    /**
     * 获得学校列表
     * @return
     */
    @RequestMapping("getSchoolList")
    public Object getSchoolList(){
        List<SchoolList> schoolList = wxSchoolService.getSchoolList();
        return WeChatResponseUtil.ok(schoolList);
    }

    /**
     * 获得学校列表，以省份分组
     */
    @RequestMapping("getSchoolListByProvince")
    public Object getSchoolListByProvince(@RequestParam("wxUserId") Integer wxUserId){
        if(wxUserId == null){
            return WeChatResponseUtil.badArgument();
        }
        List<Province> list = wxSchoolService.getSchoolListByProvince(wxUserId);
        return WeChatResponseUtil.ok(list);
    }

    /**
     * 关注部分的默认学校
     * @return
     */
    @RequestMapping("getDefaultSchoolList")
    public Object getDefaultSchoolList(){
        List<SchoolList> defaultSchoolList = wxSchoolService.getDefaultSchoolList();
        Map result = Maps.newHashMap();
        result.put("defaultSchoolList",defaultSchoolList);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过 wxUserId 获得关注学校列表
     * @param wxUserId
     * @return
     */
    @RequestMapping("getFollowSchoolById")
    public Object getFollowSchoolById(@RequestParam("wxUserId") Integer wxUserId){

        if(wxUserId==null){
            return WeChatResponseUtil.badArgument();
        }
        List<SchoolList> followSchoolList = wxSchoolService.getFollowSchoolListById(wxUserId);

        Map result = Maps.newHashMap();

        result.put("followSchoolList",followSchoolList);

        return WeChatResponseUtil.ok(result);
    }
}
