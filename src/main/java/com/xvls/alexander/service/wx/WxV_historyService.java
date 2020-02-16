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
}
