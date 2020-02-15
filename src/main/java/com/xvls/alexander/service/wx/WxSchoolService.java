package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.School;
import com.xvls.alexander.entity.wx.SchoolList;

import java.util.List;

public interface WxSchoolService {

    /**
     * 通过学校schoolId，查询学校信息
     * @param schoolId
     * @return
     */
    School getSchoolInfo(Integer schoolId,Integer wxUserId);

    List<SchoolList> getSchoolList();
}
