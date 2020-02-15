package com.xvls.alexander.dao;

import com.xvls.alexander.entity.wx.Comments;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 微信 评论 Dao
 */
@Repository
public interface WxCommentsMapper {

    /**
     * 根据 articleId 获取动态的评论信息
     * @param articleId
     * @return
     */
    List<Comments> getComments(String belongType , Integer articleId);
}
