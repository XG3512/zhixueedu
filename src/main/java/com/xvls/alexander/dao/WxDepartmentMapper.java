package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.WxDepartment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxDepartmentMapper extends BaseMapper<WxDepartment> {
    /**
     * 通过 userId 获得学院及教师列表
     * @param userId
     * @return
     */
    List<WxDepartment> getDepartmentListById(Integer userId);
}
