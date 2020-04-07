package com.xvls.alexander.service.impl;

import com.xvls.alexander.dao.LogMapper;
import com.xvls.alexander.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    LogMapper logMapper;

    @Override
    public Integer getSysLogCount() {
        return logMapper.selectCount(null);
    }
}
