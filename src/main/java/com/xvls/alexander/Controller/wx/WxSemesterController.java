package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.Semester;
import com.xvls.alexander.service.wx.WxSemesterService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/semester")
public class WxSemesterController {

    @Autowired
    WxSemesterService wxSemesterService;

    /**
     * 获得 学期 列表
     * @return
     */
    @RequestMapping("getSemesterList")
    public Object getSemesterList(){

        List<Semester> semesterList = wxSemesterService.getSemesterList();

        Map result = Maps.newHashMap();
        result.put("semesterList",semesterList);

        return WeChatResponseUtil.ok(result);
    }
}
