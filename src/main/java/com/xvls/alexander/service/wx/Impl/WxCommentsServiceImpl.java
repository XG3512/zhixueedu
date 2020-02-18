package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.dao.WxCommentsMapper;
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

    /**
     * 通过 type和id 获得评论信息
     * @param belongType
     * @param id
     * @return
     */
    @Override
    public List<Comments> getComments(String belongType , Integer id) {
        return wxCommentsMapper.getComments(belongType,id);
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


}
