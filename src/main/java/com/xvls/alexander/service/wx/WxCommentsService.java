package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Comments;

import java.util.List;

public interface WxCommentsService {

    List<Comments> getAllComments(String belongType , int articleId);
}
