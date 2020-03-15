package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Comments;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 微信 评论 Dao
 */
@Repository
public interface WxCommentsMapper extends BaseMapper<Comments> {

    /**
     * 据 parentId 获取所有子评论
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
     * 根据 belongType 和 Id 获得主评论
     * @param belongType
     * @param id
     * @param pageInfo
     * @return
     */
    List<Comments> getMainComments(String belongType , Integer id , PageInfo pageInfo);

    /**
     * 批量删除评论信息及其子评论信息
     * @param commentIdList
     */
    void deleteComment(List<Integer> commentIdList);

}
