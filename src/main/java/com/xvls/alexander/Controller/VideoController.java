package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.xvls.alexander.annotation.SysLog;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.VideoMain;
import com.xvls.alexander.entity.wx.Label;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.entity.wx.Video_episode;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.V_labelService;
import com.xvls.alexander.service.Video_mainService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.RestResponse;
import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/video")
public class VideoController {

    @Autowired
    QiniuFileUtil qiniuFileUtil;
    @Autowired
    Video_mainService video_mainService;
    @Autowired
    V_labelService v_labelService;

    /**
     * 上传视频主页面，先上传视频主页，再上传视频
     * @param mainPage
     * @param httpServletRequest
     * @return
     */
    @RequiresPermissions("video_main:add")
    @RequestMapping("uploadVideoMain")
    @SysLog("上传视频主页")
    public Object uploadVideoMain(@RequestParam("mainPage") MultipartFile mainPage,HttpServletRequest httpServletRequest){
        Video_main video_main = null;
        List<Integer> labelIdList = null;
        File_download file_download = null;

        Gson gson = new Gson();

        /*Integer teacherId = null;//教师Id
        String videoMainTitle = null;//主页标题
        String summary = null;//简介
        Boolean commentStatus = null;//评论状态
        String publicStatus = null;//播放等级*/
        try {
            /*teacherId = JacksonUtil.parseInteger(httpServletRequest.getParameter("teacherId"), null);
            videoMainTitle = JacksonUtil.parseString(httpServletRequest.getParameter("videoMainTitle"),null);
            summary = JacksonUtil.parseString(httpServletRequest.getParameter("summary"),null);
            commentStatus = JacksonUtil.parseBoolean(httpServletRequest.getParameter("commentStatus"),null);
            publicStatus = JacksonUtil.parseString(httpServletRequest.getParameter("publicStatus"),null);*/

            video_main = JacksonUtil.parseObject(httpServletRequest.getParameter("video_main"),Video_main.class);
            labelIdList = JacksonUtil.parseObject(httpServletRequest.getParameter("labelIdList"), List.class);
            /*if(file == null || teacherId==null || videoMainTitle==null || summary==null
                || publicStatus==null || commentStatus==null){
                return RestResponse.failure("参数错误");
            }*/

            if(mainPage == null || video_main == null || video_main.getTeacherId()==null || video_main.getVideoMainTitle()==null || video_main.getSummary()==null
                || video_main.getPublicStatus()==null || video_main.getCommentStatus()==null){
                return SystemResponse.badArgumentValue();
            }
            file_download = qiniuFileUtil.upload(mainPage);
            if(file_download==null){
                return SystemResponse.fail(-1,"封面上传失败");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,e.getMessage());
        }
        /*video_main.setTeacherId(teacherId);
        video_main.setVideoMainTitle(videoMainTitle);
        video_main.setSummary(summary);
        video_main.setCommentStatus(commentStatus);
        video_main.setPublicStatus(publicStatus);*/

        video_main.setMainPage(file_download.getFileUrl());
        video_main.setVideoDate(new Date());
        video_main.setEditTime(new Date());
        video_main.setVerifyStatus("审核中");
        video_main.setCollectionNum(0);
        video_main.setCommentNum(0);
        video_main.setGoodNum(0);
        video_main.setPlayNum(0);

        Integer videoMainId = video_mainService.uploadVideoMain(video_main);
        System.out.println(videoMainId);
        if(labelIdList != null){
            v_labelService.insertV_labels(videoMainId,labelIdList);
        }
        Map result = Maps.newHashMap();
        result.put("videoMainId",videoMainId);
        result.put("url",file_download.getFileUrl());
        return SystemResponse.ok(result);
    }

    /**
     * 通过 teacherId，pageInfo 获得视频列表
     * @param body
     * @return
     */
    @RequiresPermissions("video_main:select")
    @RequestMapping("getVideoMainListByTeacherId")
    public Object getVideoMainListByTeacherId(@RequestBody String body){
        Integer teacherId = null;
        PageInfo pageInfo = null;
        try {
            teacherId = JacksonUtil.parseInteger(body,"teacherId");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if (teacherId == null || pageInfo == null || pageInfo.getPageNum()==null || pageInfo.getPageSize() == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<VideoMain> videoMainList = video_mainService.getVideoListByTeacherId(teacherId, pageInfo);
        Integer videoMainPageCount = video_mainService.getVideoMainPageCount(teacherId);
        Map result = Maps.newHashMap();
        result.put("videoMainList",videoMainList);
        result.put("videoMainPageCount",videoMainPageCount);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 videoMainId 获得视频详细信息
     * @param body
     * @return
     */
    @RequiresPermissions("video_main:select")
    @RequestMapping("getVideoMainInfoById")
    public Object getVideoMainInfoById(@RequestBody String body){
        Integer videoMainId = null;
        try {
            videoMainId = JacksonUtil.parseInteger(body,"videoMainId");
            if(videoMainId == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        VideoMain videoMainInfo = video_mainService.getVideoMainInfoById(videoMainId);
        Map result = Maps.newHashMap();
        result.put("videoMainInfo",videoMainInfo);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 teacherId,content,pageInfo 进行模糊查询
     * @param body
     * @return
     */
    @RequiresPermissions("video_main:select")
    @RequestMapping("getVideoMainListByContent")
    public Object getVideoMainListByContent(@RequestBody String body){
        Integer teacherId = null;
        String content = null;
        PageInfo pageInfo = null;
        try {
            teacherId = JacksonUtil.parseInteger(body,"teacherId");
            content = JacksonUtil.parseString(body,"content");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if(teacherId == null || content == null || pageInfo == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<VideoMain> videoMainList = video_mainService.getVideoMainListByContent(teacherId, content, pageInfo);
        Integer videoMainPageCount = video_mainService.getVideoMainPageCountByContent(teacherId, content);
        Map result = Maps.newHashMap();
        result.put("videoMainList",videoMainList);
        result.put("videoMainPageCount",videoMainPageCount);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 videoMainId,labelIdList(所有标签ID),videoMainTitle,summary 修改视频主页信息
     * @param body
     * @return
     */
    @RequiresPermissions("video_main:update")
    @RequestMapping("updateMainPageInfo")
    @SysLog("修改视频主页信息 通过 videoMainId,labelIdList(所有标签ID),videoMainTitle,summary")
    public Object updateMainPageInfo(@RequestBody String body){
        Integer videoMainId = null;
        List<Integer> labelIdList = null;
        String videoMainTitle = null;
        String summary = null;
        try {
            videoMainId = JacksonUtil.parseInteger(body,"videoMainId");
            labelIdList = JacksonUtil.parseIntegerList(body,"labelIdList");
            videoMainTitle = JacksonUtil.parseString(body,"videoMainTitle");
            summary = JacksonUtil.parseString(body,"summary");

            if(videoMainId == null || labelIdList == null || labelIdList.size() == 0 || videoMainTitle == null || summary == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        v_labelService.updateV_labels(videoMainId,labelIdList);
        video_mainService.updateMainPageInfo(videoMainId,videoMainTitle,summary,new Date());
        video_mainService.editVideoMainVerifyStatus(videoMainId,"审核中");
        return SystemResponse.ok();
    }

    /**
     * 通过 videoId，videoTitle，episodeId 修改视频信息
     * @param body
     * @return
     */
    @RequiresPermissions("video_main:update")
    @RequestMapping("updateVideoInfo")
    @SysLog("修改视频信息 通过 videoId，videoTitle，episodeId")
    public Object updateVideoInfo(@RequestBody String body){
        Integer videoId = null;
        String videoTitle = null;
        Integer episodeId = null;
        try {
            videoId = JacksonUtil.parseInteger(body,"videoId");
            videoTitle = JacksonUtil.parseString(body,"videoTitle");
            episodeId = JacksonUtil.parseInteger(body,"episodeId");
            if(videoId == null || videoTitle == null || episodeId == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        video_mainService.updateVideoInfo(videoId,videoTitle,episodeId);
        video_mainService.editVideoEpisodeVerifyStatus(videoId,"审核中");
        return SystemResponse.ok();
    }

    /**
     * 通过 mainPage，videoMainId 更新视频主页图片
     * @param mainPage
     * @param videoMainId
     * @return
     */
    @RequiresPermissions("video_main:update")
    @RequestMapping("updateMainPage")
    @SysLog("更新视频主页图片 通过 mainPage，videoMainId")
    public Object updateMainPage(@RequestParam("mainPage") MultipartFile mainPage,@RequestParam("videoMainId") Integer videoMainId){
        if(mainPage == null || videoMainId == null){
            return SystemResponse.badArgument();
        }
        File_download file_download = null;
        try {
            file_download = qiniuFileUtil.upload(mainPage);
            if(file_download == null){
                return SystemResponse.fail(-1,"上传失败");
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.fail(-1,"上传失败");
        }
        video_mainService.updateMainPage(videoMainId,file_download.getFileUrl(),new Date());
        video_mainService.editVideoMainVerifyStatus(videoMainId,"审核中");
        Map result = Maps.newHashMap();
        result.put("url",file_download.getFileUrl());
        return SystemResponse.ok(result);
    }

    /**
     * 通过 videoMainIdList 批量删除视频主页信息和相关视频
     * @param body
     * @return
     */
    @RequiresPermissions("video_main:delete")
    @RequestMapping("deleteVideoMain")
    @SysLog("通过 videoMainIdList 批量删除视频主页信息和相关视频")
    public Object deleteVideoMain(@RequestBody String body){
        List<Integer> videoMainIdList = null;
        try {
            videoMainIdList = JacksonUtil.parseIntegerList(body,"videoMainIdList");
            if (videoMainIdList == null || videoMainIdList.size()==0){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        video_mainService.deleteVideoMain(videoMainIdList);
        return SystemResponse.ok();
    }

    /**
     * 通过 videoIdList 批量删除视频
     * @param body
     * @return
     */
    @RequiresPermissions("video_main:delete")
    @RequestMapping("deleteVideo")
    @SysLog("通过 videoIdList 批量删除视频")
    public Object deleteVideo(@RequestBody String body){
        List<Integer> videoIdList = null;
        try {
            videoIdList = JacksonUtil.parseIntegerList(body,"videoIdList");
            if(videoIdList == null || videoIdList.size() == 0){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        video_mainService.deleteVideo(videoIdList);
        return SystemResponse.ok();
    }

    /******************************视频审核************************************/
    /**
     * 通过 userId、verifyStatus、pageInfo 获得视频主页审核列表
     * @param body
     * @return
     */
    @RequiresPermissions("videoVerify:select")
    @RequestMapping("getVerifyVideoList")
    public Object getVerifyVideoMainList(@RequestBody String body){
        Integer userId = null;
        PageInfo pageInfo = null;
        String verifyStatus = null;
        try {
            userId = JacksonUtil.parseInteger(body,"userId");
            verifyStatus = JacksonUtil.parseString(body,"verifyStatus");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if(userId == null || verifyStatus == null || pageInfo == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<VideoMain> verifyVideoMainList = video_mainService.getVerifyVideoMainList(userId, verifyStatus, pageInfo);
        Integer verifyVideoMainCount = video_mainService.getVerifyVideoMainCount(userId, verifyStatus);
        Map result = Maps.newHashMap();
        result.put("verifyVideoMainList",verifyVideoMainList);
        result.put("videoMainCount",verifyVideoMainCount);
        return SystemResponse.ok(result);
    }


    /**
     * 通过 videoMainId,verifyStatus,pageInfo 获得视频集数列表
     * @param body
     * @return
     */
    @RequiresPermissions("videoVerify:select")
    @RequestMapping("getVerifyVideoEpisodeList")
    public Object getVerifyVideoEpisodeList(@RequestBody String body){
        Integer videoMainId = null;
        String verifyStatus = null;
        PageInfo pageInfo = null;
        try {
            videoMainId = JacksonUtil.parseInteger(body,"videoMainId");
            verifyStatus = JacksonUtil.parseString(body,"verifyStatus");
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            if (videoMainId == null || pageInfo == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<Video_episode> verifyVideoList = video_mainService.getVerifyVideoEpisodeList(videoMainId,verifyStatus,pageInfo);
        Integer videoEpisodeCount = video_mainService.getVerifyVideoEpisodeCount(videoMainId, verifyStatus);
        Map result = Maps.newHashMap();
        result.put("verifyVideoList",verifyVideoList);
        result.put("videoEpisodeCount",videoEpisodeCount);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 videoMainId，verifyStatus 修改 视频主页 审核状态
     * @param body
     * @return
     */
    @RequiresPermissions("videoVerify:update")
    @RequestMapping("editVideoMainVerifyStatus")
    @SysLog("通过 videoMainId，verifyStatus 修改 视频主页 审核状态")
    public Object editVideoMainVerifyStatus(@RequestBody String body){
        Integer videoMainId = null;
        String verifyStatus = null;
        try {
            videoMainId = JacksonUtil.parseInteger(body,"videoMainId");
            verifyStatus = JacksonUtil.parseString(body,"verifyStatus");
            if (videoMainId == null || verifyStatus == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        video_mainService.editVideoMainVerifyStatus(videoMainId,verifyStatus);
        return SystemResponse.ok();
    }

    /**
     *通过 videoId，videoMainId，verifyStatus 修改 视频 审核状态
     * @param body
     * @return
     */
    @RequiresPermissions("videoVerify:update")
    @RequestMapping("editVideoEpisodeVerifyStatus")
    @SysLog("通过 videoId，videoMainId，verifyStatus 修改 视频 审核状态")
    public Object editVideoEpisodeVerifyStatus(@RequestBody String body){
        Integer videoId = null;
        Integer videoMainId = null;
        String verifyStatus = null;
        try {
            videoId = JacksonUtil.parseInteger(body,"videoId");
            videoMainId = JacksonUtil.parseInteger(body,"videoMainId");
            verifyStatus = JacksonUtil.parseString(body,"verifyStatus");
            if (videoId == null || videoMainId == null || verifyStatus == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        video_mainService.editVideoEpisodeVerifyStatus(videoId,verifyStatus);
        if (verifyStatus.equals("通过")){
            video_mainService.editVideoMainVerifyStatus(videoMainId,"通过");
        }
        return SystemResponse.ok();
    }

}
