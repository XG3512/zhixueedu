package com.xvls.alexander.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.TcMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.Tc;
import com.xvls.alexander.service.TcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcSerticeImpl implements TcService {

    @Autowired
    TcMapper tcMapper;

    @Override
    public List<Tc> getTcList(Integer teacherId) {
        return tcMapper.getTcList(teacherId);
    }

    /**
     * 通过 teacherId，pageInfo 获得教师教学列表
     * @param teacherId
     * @return
     */
    @Override
    public List<Tc> getTcListByUserId(Integer teacherId, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return tcMapper.getTcListByUserId(teacherId,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 teacherId 获得教学信息总数目
     * @param teacherId
     * @return
     */
    @Override
    public Integer getTcCountByTeacherId(Integer teacherId) {
        QueryWrapper<Tc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",teacherId);
        return tcMapper.selectCount(queryWrapper);
    }

    /**
     * 添加教育信息
     * @param tc
     */
    @Override
    public void addTcInfo(Tc tc) {
        tcMapper.insert(tc);
    }
}
