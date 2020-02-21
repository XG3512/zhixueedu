package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.Exam;
import com.xvls.alexander.service.wx.WxExamService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 考试信息
 */
@RestController
@RequestMapping("/wx/exam")
public class WxExamController {

    @Autowired
    WxExamService wxExamService;

    /**
     * 通过userId获得考试信息
     * @param userId
     * @return
     */
    @RequestMapping("getExamListById")
    public Object getExamListById(@RequestParam("userId")Integer userId){

        if(userId == null){
            return WeChatResponseUtil.badArgument();
        }

        List<Exam> examList = wxExamService.getExamListById(userId);

        Map result = Maps.newHashMap();

        result.put("examList",examList);

        return WeChatResponseUtil.ok(result);
    }
}
