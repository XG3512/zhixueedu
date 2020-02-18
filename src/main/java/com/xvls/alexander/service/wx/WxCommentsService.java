package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Comments;

import java.util.List;

public interface WxCommentsService {

    /**
     * 获得评论信息
     * @param belongType
     * @param id
     * @return
     */
    List<Comments> getComments(String belongType , Integer id);

    /**
     * 增加评论信息
     * @param comments
     */
    Integer addComment(Comments comments);

    /**
     * 删除评论信息
     * @param comments
     */
    // TODO: 2020/2/17 删除评论功能
    void deleteComment(Comments comments);
}
