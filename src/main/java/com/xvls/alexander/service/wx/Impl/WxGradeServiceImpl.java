package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxGradeMapper;
import com.xvls.alexander.entity.wx.WxGrade;
import com.xvls.alexander.entity.wx.WxGradeAvgPoint;
import com.xvls.alexander.service.wx.WxGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学习成绩
 */
@Service
public class WxGradeServiceImpl implements WxGradeService {

    @Autowired
    WxGradeMapper wxGradeMapper;

    /**
     * 通过 userId 和 semester 查询成绩
     * @param userId
     * @param semester
     * @return
     */
    @Override
    public List<WxGrade> getGradeByIdAndSemester(Integer userId, String semester) {
        List<WxGrade> gradeList = wxGradeMapper.getGradeByIdAndSemester(userId, semester);
        return gradeList;
    }

    /**
     * 通过 userId 获得平均学分绩点
     * @param userId
     * @return
     */
    @Override
    public List<WxGradeAvgPoint> getAllSemesterAvgPoint(Integer userId) {
        return wxGradeMapper.getAllSemesterAvgPoint(userId);
    }
}
