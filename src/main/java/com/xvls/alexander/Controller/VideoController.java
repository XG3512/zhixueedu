package com.xvls.alexander.Controller;

import com.google.gson.Gson;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.entity.wx.Label;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.V_labelService;
import com.xvls.alexander.service.Video_mainService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
     * 上传视频主页面 （暂时未完善）
     * @param file
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("uploadVideoMain")
    public Object uploadVideoMain(@RequestParam("file") MultipartFile file,HttpServletRequest httpServletRequest){
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

            if(file == null || video_main.getTeacherId()==null || video_main.getVideoMainTitle()==null || video_main.getSummary()==null
                || video_main.getPublicStatus()==null || video_main.getCommentStatus()==null){
                return RestResponse.failure("参数错误");
            }

            file_download = qiniuFileUtil.upload(file);
            if(file_download==null){
                return RestResponse.failure("封面上传失败");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return RestResponse.failure(e.getMessage());
        }
        /*video_main.setTeacherId(teacherId);
        video_main.setVideoMainTitle(videoMainTitle);
        video_main.setSummary(summary);
        video_main.setCommentStatus(commentStatus);
        video_main.setPublicStatus(publicStatus);*/

        video_main.setMainPage(file_download.getFileUrl());
        video_main.setVideoDate(new Date());
        video_main.setVerifyStatus("审核中");
        video_main.setCollectionNum(0);
        video_main.setGoodNum(0);
        video_main.setPlayNum(0);


        Integer videoMainId = video_mainService.uploadVideoMain(video_main);
        System.out.println(videoMainId);

        if(labelIdList != null){
            v_labelService.insertV_labels(videoMainId,labelIdList);
        }

        return RestResponse.success();
    }
}
