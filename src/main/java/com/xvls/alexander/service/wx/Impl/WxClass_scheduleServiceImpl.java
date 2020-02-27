package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxClass_scheduleMapper;
import com.xvls.alexander.entity.wx.Class_schedule;
import com.xvls.alexander.service.wx.WxClass_scheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxClass_scheduleServiceImpl implements WxClass_scheduleService {

    @Autowired
    WxClass_scheduleMapper wxClass_scheduleMapper;

    /**
     * 更加 userId 获得课表信息
     * @param userId
     * @return
     */
    @Override
    public List<Class_schedule> getClassScheduleById(Integer userId) {
        return wxClass_scheduleMapper.getClassScheduleById(userId);
    }

    /**
     * 通过 classroomCode，dayTime，userId 获得教室一天的上课安排
     * @param classroomCode
     * @param dayTime
     * @param userId
     * @return
     */
    @Override
    public List<Class_schedule> getClassroomSchedule(String classroomCode, Integer dayTime , Integer userId) {
        return wxClass_scheduleMapper.getClassroomSchedule(classroomCode,dayTime,userId);
    }
}
