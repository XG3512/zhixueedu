package com.xvls.alexander.dao;

import com.xvls.alexander.entity.wx.Exam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 考试 Mapper
 */
@Repository
public interface WxExamMapper {

    /**
     * 通过 userId 获得考试信息
     * @param userId
     * @return
     */
    List<Exam> getExamListById(Integer userId);
}
