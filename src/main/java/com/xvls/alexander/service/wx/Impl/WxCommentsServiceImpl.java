package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxCommentsMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Comments;
import com.xvls.alexander.service.wx.WxCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论
 */
@Service
public class WxCommentsServiceImpl implements WxCommentsService {

    @Autowired
    WxCommentsMapper wxCommentsMapper;

    @Override
    public List<Comments> getMainComments(String belongType, Integer id, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum - 1)*pageSize;
        return wxCommentsMapper.getMainComments(belongType,id,new PageInfo(pageNum,pageSize));
    }

    /**
     * 根据 parentId 获取子评论
     * @param parentId
     * @return
     */
    @Override
    public List<Comments> getComments(Integer parentId) {
        return wxCommentsMapper.getComments(parentId);
    }

    /**
     * 据 parentId 获取子评论 并包括 父评论
     * @param parentId
     * @return
     */
    @Override
    public List<Comments> getComments2(Integer parentId) {
        return wxCommentsMapper.getComments2(parentId);
    }

    /**
     * 增加评论信息
     * @param comments
     */
    @Override
    public Integer addComment(Comments comments) {
        boolean insert = comments.insert();
        return comments.getCommentId();
    }

    // TODO: 2020/2/12 删除评论信息
    /**
     * 删除评论信息，前端需要判断该用户id是否与评论中的wxUserId一致
     * @param comments
     */
    @Override
    public void deleteComment(Comments comments) {

    }

    /**
     * 获得评论数
     * @param belongType
     * @param id
     * @return
     */
    @Override
    public Integer getCommentsNum(String belongType, Integer id) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("belong_type",belongType)
                .eq("belong_id",id);
        Integer count = wxCommentsMapper.selectCount(queryWrapper);
        return count;
    }


}
