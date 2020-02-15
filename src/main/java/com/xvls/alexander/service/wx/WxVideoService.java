package com.xvls.alexander.service.wx;

import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.entity.wx.Video_main;

import java.util.List;
import java.util.Map;

/**
 * 微信视频service
 */
public interface WxVideoService {

    /**
     * 获得视频列表信息
     * @return
     */
    List<Video_main> getPublicVideoList(Integer wxUserId);

    /**
     * 通过schoolId获得学校的视频信息，在学校主页面
     * @param schoolId
     * @return
     */
    List<Video_main> getPublicVideoListBySchoolId(Integer schoolId,Integer wxUserId);

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
    Video_main getVideoMainInfo(Integer videoMainId,Integer wxUserId);

    /**
     * 获得首页视频列表
     * @return
     */
    List<Video_main> getHomePageVideoList(Integer wxUserId);

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

    /**
     * 增加收藏数量
     * @param videoId
     */
    void addVideoCollectionNum(Integer videoId);

    /**
     * 减少收藏数量
     * @param videoId
     */
    void decreaseVideoCollectionNum(Integer videoId);

    /**
     * 更新视频热度
     * @param video_main
     */
    void updateVideoHeatOfVideo(Video_main video_main);
}
