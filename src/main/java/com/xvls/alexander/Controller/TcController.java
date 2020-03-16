package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.Tc;
import com.xvls.alexander.service.TcService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/tc")
public class TcController {

    @Autowired
    TcService tcService;

    /**
     * 通过 teacherId 获得教师教学列表
     * @param teacherId
     * @return
     */
    @RequiresPermissions("tc:teacher:selectList")
    @RequestMapping("getTcList")
    public Object getTcList(@RequestParam("teacherId") Integer teacherId){
        if(teacherId == null){
            return SystemResponse.badArgument();
        }
        List<Tc> tcList = tcService.getTcList(teacherId);
        Map result = Maps.newHashMap();
        result.put("tcList",tcList);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 teacherId，pageInfo 获得教师教学列表
     * @param body
     * @return
     */
    @RequestMapping("getTcListByTeacherId")
    public Object getTcListByTeacherId(@RequestBody String body){
        Integer teacherId = null;
        PageInfo pageInfo = null;
        try {
            teacherId = JacksonUtil.parseInteger(body,"teacherId");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if(teacherId == null || pageInfo == null || pageInfo.getPageNum()==null || pageInfo.getPageSize()==null){
                SystemResponse.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,e.getMessage());
        }
        List<Tc> tcList = tcService.getTcListByUserId(teacherId, pageInfo);
        Map result = Maps.newHashMap();
        result.put("tcList",tcList);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 userId , courseId , schoolId , classId , semester 添加教育信息
     * @param body
     * @return
     */
    @RequestMapping("addTcInfo")
    public Object addTcInfo(@RequestBody String body){
        Tc tc = null;
        try {
            tc = JacksonUtil.parseObject(body,Tc.class);
            if(tc.getClassId() == null || tc.getCourseId() == null || tc.getSchoolId() == null || tc.getSemester() == null || tc.getUserId() == null){
                return SystemResponse.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return SystemResponse.fail(-1,e.getMessage());
        }
        tcService.addTcInfo(tc);
        return SystemResponse.ok();
    }


    /**
     * 通过 teacherId 获得教学信息总数目
     * @param teacherId
     * @return
     */
    @RequestMapping("getTcCountByTeacherId")
    public Object getTcCountByTeacherId(@RequestParam("teacherId") Integer teacherId){
        if(teacherId==null){
            return SystemResponse.badArgument();
        }
        Integer count = tcService.getTcCountByTeacherId(teacherId);
        Map result = Maps.newHashMap();
        result.put("count",count);
        return SystemResponse.ok(result);
    }
}
