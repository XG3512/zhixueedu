package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Semester;

import java.util.List;

public interface WxSemesterService {

    /**
     * 获得 学期 列表
     * @return
     */
    List<Semester> getSemesterList();
}
