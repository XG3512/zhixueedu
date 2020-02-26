package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Comments;

import java.util.List;

public interface WxCommentsService {

    /**
     * 根据 belongType，Id，pageInfo 获得主评论
     * @param belongType
     * @param id
     * @param pageInfo
     * @return
     */
    List<Comments> getMainComments(String belongType , Integer id , PageInfo pageInfo);

    /**
     * 根据 parentId 获取子评论
     * @param parentId
     * @return
     */
    List<Comments> getComments(Integer parentId);

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
