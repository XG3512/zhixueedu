package com.xvls.alexander.Controller.wx;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Comments;
import com.xvls.alexander.service.wx.WxArticleService;
import com.xvls.alexander.service.wx.WxCommentsService;
import com.xvls.alexander.service.wx.WxVideoService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import com.xvls.alexander.utils.WeChatResponseUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/wx/comment")
public class WxCommentController {

    @Autowired
    WxCommentsService wxCommentsService;
    @Autowired
    WxVideoService wxVideoService;
    @Autowired
    WxArticleService wxArticleService;

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

    /**
     * 根据 belongType，Id，pageInfo 只获得父评论
     * @param body
     * @return
     */
    @RequestMapping("getParentComments")
    public Object getParentComments(@RequestBody String body){
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
        List<Comments> comments = wxCommentsService.getParentComments(belongType,id,pageInfo);
        Integer commentsNum = wxCommentsService.getCommentsNum(belongType, id);
        Integer parentCommentsNum = wxCommentsService.getParentCommentsNum(belongType, id);
        Map result = Maps.newHashMap();
        result.put("comments",comments);
        result.put("commentsNum",commentsNum);
        result.put("parentCommentsNum",parentCommentsNum);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 根据 belongType，Id，pageInfo 获得所有评论
     * @param body
     * @return
     */
    @RequestMapping("getAllComments")
    public Object getAllComments(@RequestBody String body){
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
        List<Comments> comments = wxCommentsService.getAllComments(belongType,id,pageInfo);
        Integer commentsNum = wxCommentsService.getCommentsNum(belongType, id);
        Integer parentCommentsNum = wxCommentsService.getParentCommentsNum(belongType, id);
        Map result = Maps.newHashMap();
        result.put("comments",comments);
        result.put("commentsNum",commentsNum);
        result.put("parentCommentsNum",parentCommentsNum);
        return WeChatResponseUtil.ok(result);
    }

    /**
     * 通过 commentIdList数组 批量删除评论信息及其子评论信息
     * @param body
     * @return
     */
    @RequiresPermissions("comments:delete")
    @RequestMapping("deleteComments")
    public Object deleteComments(@RequestBody String body){
        List<Integer> commentIdList = null;
        String belongType = null;
        Integer belongId = null;
        try {
            commentIdList = JacksonUtil.parseIntegerList(body, "commentIdList");
            belongType = JacksonUtil.parseString(body,"belongType");
            belongId = JacksonUtil.parseInteger(body,"belongId");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"Json转换出错");
        }
        if(commentIdList == null || commentIdList.size()==0 || belongId == null || belongType == null){
            return SystemResponse.badArgument();
        }
        wxCommentsService.deleteComment(commentIdList);
        /**更新评论数*/
        if(belongType.equals("A")){
            wxArticleService.updateArticleCommentsNum(belongId);
        }else if(belongType.equals("V")){
            wxVideoService.updateVideoCommentNum(belongId);
        }else{
            return SystemResponse.fail(-1,"所属类型错误");
        }

        return SystemResponse.ok();
    }




}
