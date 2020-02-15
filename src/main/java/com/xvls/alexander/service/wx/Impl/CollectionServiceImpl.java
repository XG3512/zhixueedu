package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxCollectionMapper;
import com.xvls.alexander.entity.wx.WxCollection;
import com.xvls.alexander.service.wx.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    WxCollectionMapper wxCollectionMapper;

    @Override
    public void addCollection(WxCollection collection) {
        wxCollectionMapper.insert(collection);
    }

    /**
     * 删除收藏记录
     * @param collectionType
     * @param collectionId
     * @param wxUserId
     */
    @Override
    public void deleteCollection(String collectionType, Integer collectionId, Integer wxUserId) {
        wxCollectionMapper.deleteCollection(collectionType,collectionId,wxUserId);
    }
}
