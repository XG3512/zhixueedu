package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xvls.alexander.dao.WxLabelMapper;
import com.xvls.alexander.entity.wx.Label;
import com.xvls.alexander.entity.wx.Label_type;
import com.xvls.alexander.service.wx.WxLabelService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 视频的标签
 */
@RestController
@RequestMapping("/wx/label")
public class WxLabelController {

    @Autowired
    WxLabelService wxLabelService;

    /**
     * 获得所有标签类型下的所有标签
     * @return
     */
    @RequestMapping("getAllLabelList")
    public Object getAllLabelList(){
        List<Label_type> allLabelList = wxLabelService.getAllLabelList();

        Map map = Maps.newHashMap();
        map.put("allLabelList",allLabelList);

        return WeChatResponseUtil.ok(map);
    }

    /**
     * 通过 wxUserId 获取顶部标签列表
     * @param wxUserId
     * @return
     */
    @RequestMapping("getLabelListByWxUserId")
    public Object getLabelListByWxUserId(@RequestParam("wxUserId") Integer wxUserId){
        if(wxUserId == null){
            return WeChatResponseUtil.badArgument();
        }
        Map map = Maps.newHashMap();
        List<Label> labelList = Lists.newArrayList();
        labelList.add(new Label(0,"精选"));
        List<Label> lists = wxLabelService.getLabelListByWxUserId(wxUserId);
        labelList.addAll(lists);

        map.put("labelList",labelList);

        return WeChatResponseUtil.ok(map);
    }

    /**
     * 通过 wxUserId和标签Id列表 批量删除多个标签
     * @param body
     * @return
     */
    @RequestMapping("deleteLabelByIds")
    public Object deleteLabelByIds(@RequestBody String body){
        if(body == null){
            return WeChatResponseUtil.badArgument();
        }
        Integer wxUserId = null;
        List<Integer> labelIdList = null;
        try {
            wxUserId = JacksonUtil.parseInteger(body,"wxUserId");
            labelIdList = JacksonUtil.parseIntegerList(body,"labelIdList");
        } catch (Exception e) {
            return WeChatResponseUtil.fail(-1,e.getMessage());
        }
        wxLabelService.deleteLabelByIds(wxUserId,labelIdList);
        return WeChatResponseUtil.ok();
    }

    /**
     * 通过 标签ID 添加标签
     * @param wxUserId
     * @param labelId
     * @return
     */
    @RequestMapping("addLabelById")
    public Object addLabelById(@RequestParam("wxUserId")Integer wxUserId,@RequestParam("labelId")Integer labelId){
        if(wxUserId == null || labelId == null){
            return WeChatResponseUtil.badArgument();
        }
        wxLabelService.addLabelById(wxUserId,labelId);
        return WeChatResponseUtil.ok();
    }

}
