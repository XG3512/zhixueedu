package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Notice;
import com.xvls.alexander.service.wx.WxNoticeService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.WebContentGenerator;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/notice")
public class WxNoticeController {

    @Autowired
    WxNoticeService wxNoticeService;

    /**
     * 招生信息主页的数据
     * @return
     */
    @RequestMapping("getMainPageNotice")
    public Object getMainPageNotice(){

        PageInfo pageInfo = new PageInfo(1,6);
        List<Notice> list1 = wxNoticeService.getNoticeListByClassificationPage("招生资讯", pageInfo);
        List<Notice> list2 = wxNoticeService.getNoticeListByClassificationPage("院校政策", pageInfo);
        List<Notice> list3 = wxNoticeService.getNoticeListByClassificationPage("国家政策", pageInfo);
        Map result = Maps.newHashMap();

        result.put("ZSZX",list1);
        result.put("YXZC",list2);
        result.put("GJZC",list3);

        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过 分类和分页 获得公告列表
     * @return
     */
    @RequestMapping("getNoticeByPageAndClassification")
    public Object getNoticeByPageAndClassification(@RequestBody String body){
        String classification = null;
        PageInfo pageInfo = null;
        try{
            classification = JacksonUtil.parseString(body,"classification");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if(classification==null||pageInfo==null){
                return WeChatResponseUtil.badArgument();
            }
        }catch (Exception e){
            e.printStackTrace();
            return WeChatResponseUtil.badArgument();
        }
        List<Notice> noticeList = null;
        if(classification.equals("ZSZX")){
            noticeList = wxNoticeService.getNoticeListByClassificationPage("招生资讯", pageInfo);
        }else if(classification.equals("YXZC")){
            noticeList = wxNoticeService.getNoticeListByClassificationPage("院校政策", pageInfo);
        }else if(classification.equals("GJZC")){
            noticeList = wxNoticeService.getNoticeListByClassificationPage("国家政策", pageInfo);
        }else{
            return WeChatResponseUtil.badArgument();
        }

        Map map = Maps.newHashMap();
        map.put("noticeList",noticeList);
        return WeChatResponseUtil.ok(map);
    }

    /**
     * 通过 noticeId 获取公告详情信息
     * @param noticeId
     * @return
     */
    @RequestMapping("getNoticeById")
    public Object getNoticeById(@RequestParam("noticeId") Integer noticeId){
        if(noticeId == null){
            return WeChatResponseUtil.badArgument();
        }
        Notice notice = wxNoticeService.getNoticeById(noticeId);
        return WeChatResponseUtil.ok(notice);
    }

    /**
     * 通过分页，获得最新公告信息（可用于主页的最新公告)
     * @param body
     * @return
     */
    @RequestMapping("getLatestNoticeByPage")
    public Object getLatestNoticeByPage(@RequestBody String body){
        PageInfo pageInfo = null;
        try {
            pageInfo = JacksonUtil.parseObject(body,PageInfo.class);
            if(pageInfo==null){
                return WeChatResponseUtil.badArgument();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WeChatResponseUtil.badArgument();
        }
        List<Notice> latestNotice = wxNoticeService.getLatestNoticeByPage(pageInfo);
        Map result = Maps.newHashMap();
        result.put("latestNotice",latestNotice);
        return WeChatResponseUtil.ok(result);
    }
}
