package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.entity.School;
import com.xvls.alexander.service.wx.WxSchoolService;
import com.xvls.alexander.utils.CalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 获取学校信息
 */
@Service
public class WxSchoolServiceImpl implements WxSchoolService {

    @Autowired
    WxSchoolService wxSchoolService;

    /**
     * 根据schoolId，获取学校信息
     * @param schoolId
     * @return
     */
    @Override
    public School getSchoolInfo(Integer schoolId) {
        School school = new School();
        school = school.selectById(schoolId);
        school.setGoodNumText(CalculateUtil.CalculateNum(school.getGoodNum()));
        school.setCollectionNumText(CalculateUtil.CalculateNum(school.getCollectionNum()));
        school.setFansNumText(CalculateUtil.CalculateNum(school.getFansNum()));
        return school;
    }
}
