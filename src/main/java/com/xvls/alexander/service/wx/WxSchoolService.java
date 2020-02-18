package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.School;
import com.xvls.alexander.entity.wx.Province;
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

    List<Province> getSchoolListByProvince(Integer wxUserId);

    /**
     * 更新学校的背景图
     * @param schoolId
     * @param file_url
     */
    void updateSchoolBackground(Integer schoolId,String file_url);

    /**
     * 更新学校头像
     * @param schoolId
     * @param file_url
     */
    void updateSchoolHead(Integer schoolId,String file_url);
}
