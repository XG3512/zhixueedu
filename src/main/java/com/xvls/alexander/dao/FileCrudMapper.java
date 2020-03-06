package com.xvls.alexander.dao;

import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface FileCrudMapper {

    List<File_belong> getFileList(int userId, int m ,int n);

    /**
     * 插入多个file_belongs
     * @param file_belongs
     */
    void insertFileBelong(@Param("file_belongs") List<File_belong> file_belongs);

}
