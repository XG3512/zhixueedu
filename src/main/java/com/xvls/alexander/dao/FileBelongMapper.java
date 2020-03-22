package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.File_belong;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileBelongMapper extends BaseMapper<File_belong> {

    /**
     * 通过 articleIdList 批量删除FileBelong
     * @param articleIdList
     */
    void deleteFileBelongByArticleIdList(List<Integer> articleIdList);
}
