package com.xvls.alexander.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 视频-标签
 */
@Repository
public interface WxV_labelMapper {

    void insertV_labels(Integer videoMainId,List<Integer> v_labelIdList);
}
