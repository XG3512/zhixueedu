package com.xvls.alexander.service;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.Tc;

import java.util.List;

public interface TcService {

    /**
     * 通过 teacherId 获得教师教学列表
     * @param teacherId
     * @return
     */
    List<Tc> getTcList(Integer teacherId);

    /**
     * 通过 teacherId，pageInfo 获得教师教学列表
     * @param teacherId
     * @return
     */
    List<Tc> getTcListByUserId(Integer teacherId, PageInfo pageInfo);

    /**
     * 通过 teacherId 获得教学信息总数目
     * @param teacherId
     * @return
     */
    Integer getTcCountByTeacherId(Integer teacherId);

    /**
     * 添加教育信息
     * @param tc
     */
    void addTcInfo(Tc tc);
}
