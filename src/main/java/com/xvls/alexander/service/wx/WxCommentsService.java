package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Comments;

import java.util.List;

public interface WxCommentsService {

    /**
     * 获得评论信息
     * @param belongType
     * @param articleId
     * @return
     */
    List<Comments> getAllComments(String belongType , int articleId);

    /**
     * 增加评论信息
     * @param comments
     */
    // TODO: 2020/2/9 addComment
    void addComment(Comments comments);

    /**
     * 删除评论信息
     * @param comments
     */
    // TODO: 2020/2/9 deleteComment
    void deleteComment(Comments comments);
}
