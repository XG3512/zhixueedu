package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.V_history;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 视频记录
 */
@Repository
public interface WxV_historyMapper extends BaseMapper<V_history> {

    /**
     * 更新历史记录
     * @param v_history
     */
    void updateV_history(V_history v_history);

    /**
     * 通过 wxUserId 和 pageInfo 获得视频历史记录
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<V_history> getV_historyByIdAndPage(Integer wxUserId, PageInfo pageInfo);
}
