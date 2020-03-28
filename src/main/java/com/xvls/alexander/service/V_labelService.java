package com.xvls.alexander.service;

import java.util.List;

public interface V_labelService {

    /**
     * 为视频主页插入标签
     * @param videoMainId
     * @param v_labelIdList
     */
    void insertV_labels(Integer videoMainId, List<Integer> v_labelIdList);

    /**
     * 更新标签
     * @param videoMainId
     * @param v_labelIdList
     */
    void updateV_labels(Integer videoMainId,List<Integer> v_labelIdList);

    /**
     * 通过 videoMainId 删除视频标签
     * @param videoMainId
     */
    void deleteV_labelsByMainId(Integer videoMainId);
}
