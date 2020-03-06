package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.SemesterMapper;
import com.xvls.alexander.entity.wx.Semester;
import com.xvls.alexander.service.wx.WxSemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxSemesterServiceImpl implements WxSemesterService {

    @Autowired
    SemesterMapper semesterMapper;

    /**
     * 获得 学期 列表
     * @return
     */
    @Override
    public List<Semester> getSemesterList() {
        QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
        return semesterMapper.selectList(null);
    }
}
