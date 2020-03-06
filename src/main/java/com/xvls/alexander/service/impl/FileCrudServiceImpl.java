package com.xvls.alexander.service.impl;

import com.xvls.alexander.dao.FileCrudMapper;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.service.FileCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileCrudServiceImpl implements FileCrudService {

    @Autowired
    FileCrudMapper fileCrudMapper;
    /**
     * 通过用户id，获取文件信息列表
     * @param userId
     * @param pageInfo
     * @return
     */
    @Override
    public List<File_belong> getFileList(int userId,PageInfo pageInfo) {
        int pnum = pageInfo.getPageNum();
        int psize = pageInfo.getPageSize();
        return fileCrudMapper.getFileList(userId,(pnum-1)*psize,psize);
    }

    /**
     * 插入多个file_belongs
     * @param file_belongs
     */
    @Override
    public void insertFileBelong(List<File_belong> file_belongs) {
        fileCrudMapper.insertFileBelong(file_belongs);
    }

    @Override
    public void deleteFiles() {

    }
}
