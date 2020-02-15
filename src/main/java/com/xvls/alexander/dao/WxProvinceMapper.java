package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.Province;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxProvinceMapper extends BaseMapper<Province> {

    List<Province> getSchoolListByProvince(Integer wxUserId);
}
