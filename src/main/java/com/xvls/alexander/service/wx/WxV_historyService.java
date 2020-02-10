package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.V_history;

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
     * 通过v_history_id获得历史记录
     * @param vHistoryId
     * @return
     */
    V_history getV_historyById(Integer vHistoryId);
}
