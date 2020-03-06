package com.xvls.alexander.service;

import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.PageInfo;

import java.util.List;

public interface FileCrudService {

    List<File_belong> getFileList(int userId, PageInfo pageInfo);

    /**
     * 插入多个file_belongs
     * @param file_belongs
     */
    void insertFileBelong(List<File_belong> file_belongs);

    void deleteFiles();
}
