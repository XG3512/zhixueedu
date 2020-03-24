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
     * 据 parentId 获取子评论 并包括 父评论
     * @param parentId
     * @return
     */
    List<Comments> getComments(Integer parentId);

    /**
     * 据 parentId 获取子评论 并包括 父评论
     * @param parentId
     * @return
     */
    List<Comments> getComments2(Integer parentId);

    /**
     * 根据 belongType，Id，pageInfo 只获得父评论
     * @param belongType
     * @param id
     * @param pageInfo
     * @return
     */
    List<Comments> getParentComments(String belongType , Integer id , PageInfo pageInfo);

    /**
     * 根据 belongType，Id，pageInfo 获得所有评论
     * @param belongType
     * @param id
     * @param pageInfo
     * @return
     */
    List<Comments> getAllComments(String belongType , Integer id , PageInfo pageInfo);

    /**
     * 增加评论信息
     * @param comments
     */
    Integer addComment(Comments comments);

    /**
     * 批量删除评论信息及其子评论信息
     * @param commentIdList
     */
    void deleteComment(List<Integer> commentIdList);

    /**
     * 获得评论数
     * @param belongType
     * @param id
     * @return
     */
    Integer getCommentsNum(String belongType , Integer id);

    /**
     * 获得父评论数
     * @param belongType
     * @param id
     * @return
     */
    Integer getParentCommentsNum(String belongType,Integer id);
}
