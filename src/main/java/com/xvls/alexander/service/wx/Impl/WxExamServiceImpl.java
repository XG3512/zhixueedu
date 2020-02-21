package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxExamMapper;
import com.xvls.alexander.entity.wx.Exam;
import com.xvls.alexander.service.wx.WxExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class WxExamServiceImpl implements WxExamService {

    @Autowired
    WxExamMapper wxExamMapper;

    /**
     * 通过 userId 获得考试信息
     * @param userId
     * @return
     */
    @Override
    public List<Exam> getExamListById(Integer userId) {
        return wxExamMapper.getExamListById(userId);
    }
}
