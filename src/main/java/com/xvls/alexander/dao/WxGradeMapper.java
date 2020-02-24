package com.xvls.alexander.dao;

import com.xvls.alexander.entity.wx.WxGrade;
import com.xvls.alexander.entity.wx.WxGradeAvgPoint;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxGradeMapper {

    /**
     * 通过 userId 和 semester 查询成绩
     * @param userId
     * @param semester
     * @return
     */
    List<WxGrade> getGradeByIdAndSemester(Integer userId, String semester);

    /**
     * 通过 userId 获得平均学分绩点
     * @param userId
     * @return
     */
    List<WxGradeAvgPoint> getAllSemesterAvgPoint(Integer userId);
}
