package com.xvls.alexander.service.wx;


import com.xvls.alexander.entity.wx.Exam;

import java.util.List;

/**
 * 考试信息Service
 */
public interface WxExamService {

    /**
     * 通过 userId 获得考试信息
     * @param userId
     * @return
     */
    List<Exam> getExamListById(Integer userId);
}
