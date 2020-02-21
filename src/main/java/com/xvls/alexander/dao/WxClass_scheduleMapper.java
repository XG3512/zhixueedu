package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.Class_schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxClass_scheduleMapper extends BaseMapper<Class_schedule> {

    /**
     * 根据 userId 获得课表信息
     * @param userId
     * @return
     */
    List<Class_schedule> getClassScheduleById(Integer userId);
}
