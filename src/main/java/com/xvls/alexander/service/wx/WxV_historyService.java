package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.V_history;

import java.util.List;

/**
 * 观看历史
 */
public interface WxV_historyService {

    /**
     * 添加历史记录
     * @param v_history
     */
    void addV_history(V_history v_history);

    /**
     * 更新历史记录
     * @param v_history
     */
    void updateV_history(V_history v_history);

    /**
     * 通过v_history_id获得历史记录
     * @param videoMainId
     * @param wxUserId
     * @return
     */
    V_history getV_historyById(Integer videoMainId,Integer wxUserId);

    /**
     * 通过 wxUserId 和 pageInfo 获得视频历史记录
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<V_history> getV_historyByIdAndPage(Integer wxUserId, PageInfo pageInfo);
}
