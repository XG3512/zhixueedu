package com.xvls.alexander.service.wx;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Label;
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
     * 通过 分页,标签,wxUserId 获得视频列表
     * @param pageInfo
     * @param labelId
     * @param wxUserId
     * @return
     */
    List<Video_main> getPublicVideoListByLabel(PageInfo pageInfo, Integer labelId , Integer wxUserId);



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

    /**
     * 通过 wxUserId、teacherId和pageInfo 获得教师主页的视频列表
     * @param wxUserInfo
     * @param teacherId
     * @param pageInfo
     * @return
     */
    List<Video_main> getPublicVideoListbyTeacherId(Integer wxUserInfo,Integer teacherId,PageInfo pageInfo);

    /**
     * 通过 episodeId 和 videoMainId 查找视频信息
     * @param episodeId
     * @param videoMainId
     * @return
     */
    Video getVideoByEpisodeId_VideoMainId(Integer episodeId,Integer videoMainId);

    /**
     * 更新视频地址
     * @param video
     */
    void updateVideoById(Video video);

    /**
     * 插入视频
     * @param video
     * @return
     */
    Integer insertVideo(Video video);

    /**
     * 通过 wxUserId , pageInfo 获得视频 点赞 列表
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<Video_main> getVideoGoodList(Integer wxUserId, PageInfo pageInfo);

    /**
     * 通过 wxUserId , pageInfo 获得视频 收藏 列表
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    List<Video_main> getVideoCollectionList(Integer wxUserId, PageInfo pageInfo);
}
