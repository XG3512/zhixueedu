package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Class_schedule;

import java.util.List;

/**
 * 课表Service
 */
public interface WxClass_scheduleService {

    /**
     * 根据 userId 获得课表信息
     * @param userId
     * @return
     */
    List<Class_schedule> getClassScheduleById(Integer userId);

    /**
     * 通过 classroomCode，dayTime，userId 获得教室一天的上课安排
     * @param classroomCode
     * @param dayTime
     * @param userId
     * @return
     */
    List<Class_schedule> getClassroomSchedule(String classroomCode,Integer dayTime , Integer userId);
}
