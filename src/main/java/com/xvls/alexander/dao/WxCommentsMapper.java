package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.Comments;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 微信 评论 Dao
 */
@Repository
public interface WxCommentsMapper extends BaseMapper<Comments> {

    /**
     * 根据 articleId 获取动态的评论信息
     * @param belongType
     * @param id
     * @return
     */
    List<Comments> getComments(String belongType , Integer id);
}
