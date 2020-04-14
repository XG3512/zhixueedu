package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.Good;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 点赞Mapper
 */
@Repository
public interface WxGoodMapper extends BaseMapper<Good> {

    /**
     * 通过 belongType，belongIdList 批量删除点赞记录
     * @param belongType
     * @param belongIdList
     */
    void deleteGoods(String belongType, List<Integer> belongIdList);
}
