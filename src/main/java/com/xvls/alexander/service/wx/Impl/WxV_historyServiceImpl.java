package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxV_historyMapper;
import com.xvls.alexander.entity.wx.V_history;
import com.xvls.alexander.service.wx.WxV_historyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 观看历史
 */
@Service
public class WxV_historyServiceImpl implements WxV_historyService {

    @Autowired
    WxV_historyMapper wxV_historyMapper;

    /**
     * 添加历史记录
     * @param v_history
     */
    @Override
    public void addV_history(V_history v_history) {
        V_history v_historyById = this.getV_historyById(v_history.getVHistoryId());
        if(v_historyById!=null){
            wxV_historyMapper.updateById(v_history);
        }else{
            wxV_historyMapper.insert(v_history);
        }
    }

    /**
     * 通过v_history_id获得历史记录
     * @param vHistoryId
     * @return
     */
    @Override
    public V_history getV_historyById(Integer vHistoryId) {
        return wxV_historyMapper.selectById(vHistoryId);
    }
}
