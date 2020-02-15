package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.WxCollection;

/**
 * 收藏Service
 */
public interface CollectionService {

    void addCollection(WxCollection collection);

    void deleteCollection(String collectionType,Integer collectionId,Integer wxUserId);
}
