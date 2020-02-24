package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.wx.WxGrade;
import com.xvls.alexander.entity.wx.WxGradeAvgPoint;
import com.xvls.alexander.service.wx.WxGradeService;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 教务系统中的成绩
 */
@RestController
@RequestMapping("/wx/grade")
public class WxGradeController {

    @Autowired
    WxGradeService wxGradeService;

    /**
     * 通过 userId 和 semester 查询成绩
     * @param userId
     * @param semester
     * @return
     */
    @RequestMapping("getGradeByIdAndSemester")
    public Object getGradeByIdAndSemester(@RequestParam("userId") Integer userId,@RequestParam("semester") String semester){

        if(userId == null || semester == null){
            return WeChatResponseUtil.badArgument();
        }

        List<WxGrade> gradeList = wxGradeService.getGradeByIdAndSemester(userId, semester);
        Iterator it = gradeList.iterator();
        Float gradeSum = 0f;
        Float creditSum = 0f;
        while(it.hasNext()){
            WxGrade next = (WxGrade) it.next();
            gradeSum += next.getGradePoint()*next.getCredit();
            creditSum += next.getCredit();
        }
        Float avgPoint = gradeSum/creditSum;
        Map result = Maps.newHashMap();
        result.put("avgPoint",avgPoint);
        result.put("gradeList",gradeList);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过 userId 获得所有学期的平均学分绩点
     * @param userId
     * @return
     */
    @RequestMapping("getAllSemesterAvgPoint")
    public Object getAllSemesterAvgPoint(@RequestParam("userId") Integer userId){

        if(userId==null){
            return WeChatResponseUtil.badArgument();
        }

        List<WxGradeAvgPoint> allSemesterAvgPoint = wxGradeService.getAllSemesterAvgPoint(userId);

        Map result = Maps.newHashMap();

        result.put("allSemesterAvgPoint",allSemesterAvgPoint);

        return WeChatResponseUtil.ok(result);
    }
}
