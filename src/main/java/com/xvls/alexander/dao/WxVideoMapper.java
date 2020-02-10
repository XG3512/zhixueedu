package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.entity.wx.Video_main;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxVideoMapper extends BaseMapper<Video_main> {

    /**
     * 获得视频列表信息
     * @return
     */
    List<Video_main> getPublicVideoList();

    /**
     * 添加视频（还没做）
     * @param video_main
     * @param video
     */
    // TODO: 2020/2/9 添加视频功能
    void addVideo(Video_main video_main, Video video);

    /**
     * 通过schoolId获得视频列表，用于学校主页面
     * @param schoolId
     * @return
     */
    List<Video_main> getPublicVideoListBySchoolId(Integer schoolId);

    /**
     * 通过videoMainId,episodeId,获取该集视频的详细信息
     * @param videoMainId
     * @param episodeId
     * @return
     */
    Video getVideoInfoById(Integer videoMainId , Integer episodeId);

    /**
     * 通过videoMainId获取该主页面的视频信息
     * @param videoMainId
     * @return
     */
    Video_main getVideoMainInfo(Integer videoMainId);

    /**
     * 视频点赞数增加
     * @param videoId
     */
    void addVideoGoodNum(Integer videoId);

    /**
     * 视频评论数增加
     * @param videoId
     */
    void addVideoCommentNum(Integer videoId);

    /**
     * 视频浏览量增加
     * @param videoId
     */
    void addVideoPlayNum(Integer videoId);

    /**
     * 视频点赞数减少
     * @param videoId
     */
    void decreaseVideoGoodNum(Integer videoId);

    /**
     * 视频评论数减少
     * @param videoId
     */
    void decreaseVideoCommentNum(Integer videoId);
}
