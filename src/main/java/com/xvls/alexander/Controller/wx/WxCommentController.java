package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Comments;
import com.xvls.alexander.service.wx.WxCommentsService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/comment")
public class WxCommentController {

    @Autowired
    WxCommentsService wxCommentsService;

    /**
     * 根据 belongType，Id，pageInfo 获得主评论
     * @param body
     * @return
     */
    @RequestMapping("getMainComments")
    public Object getMainComments(@RequestBody String body){
        String belongType = null;
        Integer id = null;
        PageInfo pageInfo = null;

        try {
            belongType = JacksonUtil.parseString(body,"belongType");
            id = JacksonUtil.parseInteger(body,"id");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);

            if(belongType == null || id == null || pageInfo == null || pageInfo.getPageNum()==null || pageInfo.getPageSize()==null){
                return WeChatResponseUtil.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return WeChatResponseUtil.badArgument();
        }

        List<Comments> mainComments = wxCommentsService.getMainComments(belongType, id, pageInfo);
        Integer commentsNum = wxCommentsService.getCommentsNum(belongType, id);
        Map result = Maps.newHashMap();
        result.put("mainComments",mainComments);
        result.put("commentsNum",commentsNum);

        return WeChatResponseUtil.ok(result);
    }

    /**
     * 据 parentId 获取所有子评论
     * @param parentId
     * @return
     */
    @RequestMapping("getComments")
    public Object getComments(@RequestParam("parentId") Integer parentId){
        if(parentId == null){
            return WeChatResponseUtil.badArgument();
        }

        List<Comments> comments = wxCommentsService.getComments(parentId);
        Map result = Maps.newHashMap();
        result.put("comments",comments);

        return WeChatResponseUtil.ok(result);
    }

    /**
     * 据 parentId 获取子评论 并包括 父评论
     * @param parentId
     * @return
     */
    @RequestMapping("getComments2")
    public Object getComments2(@RequestParam("parentId") Integer parentId){
        if(parentId == null){
            return WeChatResponseUtil.badArgument();
        }

        List<Comments> comments = wxCommentsService.getComments2(parentId);
        Map result = Maps.newHashMap();
        result.put("comments",comments);

        return WeChatResponseUtil.ok(result);
    }


}
