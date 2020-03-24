package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Notice;
import com.xvls.alexander.service.wx.WxNoticeService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/notice")
public class NoticeController {

    @Autowired
    WxNoticeService wxNoticeService;

    /**
     * 通过 pageInfo,userId 获得通知列表
     * @param body
     * @return
     */
    @RequiresPermissions("notice:select")
    @RequestMapping("getNoticeList")
    public Object getNoticeList(@RequestBody String body){
        PageInfo pageInfo = null;
        Integer userId = null;
        try {
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            userId = JacksonUtil.parseInteger(body,"userId");
            if(pageInfo == null || userId == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<Notice> noticeList = wxNoticeService.getNoticeList(pageInfo, userId);
        Map result = Maps.newHashMap();
        result.put("noticeList",noticeList);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 userId 获得通知总条目数
     * @param body
     * @return
     */
    @RequiresPermissions("notice:select")
    @RequestMapping("getNoticeCount")
    public Object getNoticeCount(@RequestBody String body){
        Integer userId = null;
        try {
            userId = JacksonUtil.parseInteger(body,"userId");
            if(userId == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        Integer noticeCount = wxNoticeService.getNoticeCount(userId);
        Map result = Maps.newHashMap();
        result.put("noticeCount",noticeCount);
        return SystemResponse.ok(result);
    }

    /**
     * 通知增加功能，schoolId，title，content，noticeText，noticeTime，classification，infoFrom
     * @param body
     * @return
     */
    @RequiresPermissions("notice:add")
    @RequestMapping("addNotice")
    public Object addNotice(@RequestBody String body){
        Integer schoolId = null;//学校ID
        String title = null;//标题
        String content = null;//内容
        String noticeText = null;//文字信息
        Date noticeTime = null;//时间
        String classification = null;//信息类别
        String infoFrom = null;//信息来源

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            schoolId = JacksonUtil.parseInteger(body,"schoolId");
            title = JacksonUtil.parseString(body,"title");
            content = JacksonUtil.parseString(body,"content");
            noticeText = JacksonUtil.parseString(body,"noticeText");
            noticeTime = dateFormat.parse(JacksonUtil.parseString(body,"noticeTime"));
            classification = JacksonUtil.parseString(body,"classification");
            infoFrom = JacksonUtil.parseString(body,"infoFrom");
            if(schoolId == null || title == null || content == null || noticeText == null || classification == null || classification == null || infoFrom == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        Notice notice = new Notice();
        notice.setSchoolId(schoolId);
        notice.setTitle(title);
        notice.setContent(content);
        notice.setNoticeText(noticeText);
        notice.setReadNum(0);
        if(noticeTime == null){
            notice.setNoticeTime(new Date());
        }else{
            notice.setNoticeTime(noticeTime);
        }
        notice.setGoodNum(0);
        notice.setCommentNum(0);
        notice.setCommentStatus(false);
        notice.setClassification(classification);
        notice.setInfoFrom(infoFrom);

        Integer noticeId = wxNoticeService.addNotice(notice);
        Map result = Maps.newHashMap();
        result.put("noticeId",noticeId);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 noticeIdList数组 批量删除通知
     * @param bdoy
     * @return
     */
    @RequiresPermissions("notice:delete")
    @RequestMapping("deleteNotices")
    public Object deleteNotices(@RequestBody String bdoy){
        List<Integer> noticeIdList = null;
        try {
            noticeIdList = JacksonUtil.parseIntegerList(bdoy,"noticeIdList");
            if(noticeIdList == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        wxNoticeService.deleteNotices(noticeIdList);
        return SystemResponse.ok();
    }
}
