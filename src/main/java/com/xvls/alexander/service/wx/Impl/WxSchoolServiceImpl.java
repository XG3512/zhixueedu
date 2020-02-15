package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxProvinceMapper;
import com.xvls.alexander.dao.WxSchoolMapper;
import com.xvls.alexander.entity.School;
import com.xvls.alexander.entity.wx.Province;
import com.xvls.alexander.entity.wx.SchoolList;
import com.xvls.alexander.service.wx.WxSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取学校信息
 */
@Service
public class WxSchoolServiceImpl implements WxSchoolService {

    @Autowired
    WxSchoolMapper wxSchoolMapper;
    @Autowired
    WxProvinceMapper wxProvinceMapper;

    /**
     * 根据schoolId，获取学校信息
     * @param schoolId
     * @return
     */
    @Override
    public School getSchoolInfo(Integer schoolId,Integer wxUserId) {
        return wxSchoolMapper.getSchoolInfo(schoolId,wxUserId);
    }

    /**
     * 获得学校列表，通过省份降序排列
     * @return
     */
    @Override
    public List<SchoolList> getSchoolList() {
        return wxSchoolMapper.getSchoolList();
    }

    @Override
    public List<Province> getSchoolListByProvince(Integer wxUserId) {
        return wxProvinceMapper.getSchoolListByProvince(wxUserId);
    }
}
