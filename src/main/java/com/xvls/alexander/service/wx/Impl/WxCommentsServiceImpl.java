package com.xvls.alexander.service.wx.Impl;

import com.xvls.alexander.entity.wx.Comments;
import com.xvls.alexander.service.wx.WxCommentsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论
 */
@Service
public class WxCommentsServiceImpl implements WxCommentsService {
    /**
     * 通过动态id获得评论信息
     * @param belongType
     * @param articleId
     * @return
     */
    @Override
    public List<Comments> getAllComments(String belongType , int articleId) {
        return null;
    }
}
