package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.School;
import com.xvls.alexander.entity.wx.SchoolList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxSchoolMapper extends BaseMapper<School> {


    /**
     * 根据schoolId，获取学校信息
     * @param schoolId
     * @param wxUserId
     * @return
     */
    School getSchoolInfo(Integer schoolId,Integer wxUserId);

    /**
     * 获得学校列表，通过省份降序排列
     * @return
     */
    List<SchoolList> getSchoolList();
}
