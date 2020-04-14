package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.WxCollection;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxCollectionMapper extends BaseMapper<WxCollection> {

    /**
     * 删除收藏记录
     * @param collectionType
     * @param collectionId
     * @param wxUserId
     */
    void deleteCollection(String collectionType, Integer collectionId, Integer wxUserId);

    /**
     * 通过 belongType,belongIdList 批量删除收藏记录
     * @param belongType
     * @param belongIdList
     */
    void deleteCollections(String belongType, List<Integer> belongIdList);
}
