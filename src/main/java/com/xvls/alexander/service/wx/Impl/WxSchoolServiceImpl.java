package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxFollow_schoolMapper;
import com.xvls.alexander.dao.WxProvinceMapper;
import com.xvls.alexander.dao.WxSchoolMapper;
import com.xvls.alexander.entity.School;
import com.xvls.alexander.entity.wx.Follow_school;
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
    @Autowired
    WxFollow_schoolMapper wxFollow_schoolMapper;

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

    /**
     * 更新学校的背景图
     * @param schoolId
     * @param file_url
     */
    @Override
    public void updateSchoolBackground(Integer schoolId, String file_url) {
        wxSchoolMapper.updateSchoolBackground(schoolId,file_url);
    }

    /**
     * 更新学校头像
     * @param schoolId
     * @param file_url
     */
    @Override
    public void updateSchoolHead(Integer schoolId, String file_url) {
        wxSchoolMapper.updateSchoolHead(schoolId,file_url);
    }

    /**
     * 关注部分的默认学校
     * @return
     */
    @Override
    public List<SchoolList> getDefaultSchoolList() {
        return wxSchoolMapper.getDefaultSchoolList();
    }

    /**
     * 通过 wxUserId 获得关注学校列表
     * @param wxUserId
     * @return
     */
    @Override
    public List<SchoolList> getFollowSchoolListById(Integer wxUserId) {
        return wxSchoolMapper.getFollowSchoolListById(wxUserId);
    }

    /**
     * 增加粉丝数
     * @param schoolId
     */
    @Override
    public void addSchoolFansNum(Integer schoolId) {
        wxSchoolMapper.addSchoolFansNum(schoolId);
    }

    /**
     * 减少粉丝数
     * @param schoolId
     */
    @Override
    public void decreaseSchoolFansNum(Integer schoolId) {
        wxSchoolMapper.decreaseSchoolFansNum(schoolId);
    }

    @Override
    public void updateSchoolGoodCollectionNum(Integer schoolId) {
        wxSchoolMapper.updateSchoolGoodCollectionNum(schoolId);
    }

    /**
     * 获得 学校 总数
     * @return
     */
    @Override
    public Integer getAllSchoolCount() {
        return wxSchoolMapper.selectCount(null);
    }


}
