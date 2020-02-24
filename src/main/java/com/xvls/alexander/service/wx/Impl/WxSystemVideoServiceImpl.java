package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxDepartmentMapper;
import com.xvls.alexander.entity.wx.WxDepartment;
import com.xvls.alexander.service.wx.WxSystemVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxSystemVideoServiceImpl implements WxSystemVideoService {

    @Autowired
    WxDepartmentMapper wxDepartmentMapper;

    /**
     * 通过 userId 获得学院及教师列表
     * @param userId
     * @return
     */
    @Override
    public List<WxDepartment> getDepartmentListById(Integer userId) {
        return wxDepartmentMapper.getDepartmentListById(userId);
    }
}
