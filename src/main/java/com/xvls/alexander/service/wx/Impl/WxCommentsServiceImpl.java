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
     * 通过动态id获得评论信息
     * @param belongType
     * @param articleId
     * @return
     */
    @Override
    public List<Comments> getAllComments(String belongType , int articleId) {
        return wxCommentsMapper.getAllComments(belongType,articleId);
    }

    /**
     * 增加评论信息
     * @param comments
     */
    @Override
    public void addComment(Comments comments) {

    }

    /**
     * 删除评论信息
     * @param comments
     */
    @Override
    public void deleteComment(Comments comments) {

    }


}
