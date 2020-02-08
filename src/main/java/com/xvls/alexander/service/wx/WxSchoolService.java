package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.School;

public interface WxSchoolService {

    /**
     * 通过学校schoolId，查询学校信息
     * @param schoolId
     * @return
     */
    School getSchoolInfo(Integer schoolId);
}
