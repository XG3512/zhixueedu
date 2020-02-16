package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        QueryWrapper<V_history> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_main_id",v_history.getVideoMainId())
                .eq("wx_user_id",v_history.getWxUserId());
        V_history v_historyById = wxV_historyMapper.selectOne(queryWrapper);
        if(v_historyById!=null){
            this.updateV_history(v_history);
        }else{
            wxV_historyMapper.insert(v_history);
        }
    }

    @Override
    public void updateV_history(V_history v_history) {
        wxV_historyMapper.updateV_history(v_history);
    }

    /**
     * 通过v_history_id获得历史记录
     * @param videoMainId
     * @param wxUserId
     * @return
     */
    @Override
    public V_history getV_historyById(Integer videoMainId,Integer wxUserId) {
        QueryWrapper<V_history> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_main_id",videoMainId)
                .eq("wx_user_id",wxUserId);
        return wxV_historyMapper.selectOne(queryWrapper);
    }
}
