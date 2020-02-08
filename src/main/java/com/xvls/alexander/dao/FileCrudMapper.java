package com.xvls.alexander.dao;

import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.PageInfo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface FileCrudMapper {

    List<File_belong> getFileList(int userId, int m ,int n);

}
