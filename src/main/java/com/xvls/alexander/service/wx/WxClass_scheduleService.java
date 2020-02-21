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
}
