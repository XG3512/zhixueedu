package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.Tc;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TcMapper extends BaseMapper<Tc> {

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
}
