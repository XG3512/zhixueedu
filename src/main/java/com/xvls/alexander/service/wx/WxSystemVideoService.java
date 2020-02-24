package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.WxDepartment;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface WxSystemVideoService {

    /**
     * 通过 userId 获得学院及教师列表
     * @param userId
     * @return
     */
    List<WxDepartment> getDepartmentListById(Integer userId);
}
