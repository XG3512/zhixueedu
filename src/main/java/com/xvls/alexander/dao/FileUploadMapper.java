package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.File_download;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadMapper extends BaseMapper<File_download> {

    File_download selectOneByHash(String file_hash);
}
