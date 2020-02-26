package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Label;
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
    List<Video_main> getPublicVideoList(Integer wxUserId);

    /**
     * 添加视频（还没做）
     * @param video_main
     * @param video
     */
    // TODO: 2020/2/9 添加视频功能
    void addVideo(Video_main video_main, Video video);

    /**
     * 通过 schoolId,wxUserId,pageInfo 获得学校的视频信息，在学校主页面
     * @param schoolId
     * @param pageInfo
     * @param wxUserId
     * @return
     */
    List<Video_main> getPublicVideoListBySchoolId(Integer schoolId,Integer wxUserId,PageInfo pageInfo);

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
     * 获得主页的视频列表
     * @return
     */
    List<Video_main> getHomePageVideoList(PageInfo pageInfo);

    /**
     * 通过 分页,标签,wxUserId 获得视频列表
     * @param pageInfo
     * @param labelId
     * @return
     */
    List<Video_main> getPublicVideoListByLabel(PageInfo pageInfo, Integer labelId , Integer wxUserId);

    /**
     * 通过 pageInfo , wxUserId 获得精选页面的视频列表（按照热度排序）
     * @param pageInfo
     * @param wxUserId
     * @return
     */
    List<Video_main> getMainPagePublicVideoList(PageInfo pageInfo , Integer wxUserId);


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
     * 视频收藏数增加
     * @param videoId
     */
    void addVideoCollectionNum(Integer videoId);

    /**
     * 视频收藏数量减少
     * @param videoId
     */
    void decreaseVideoCollectionNum(Integer videoId);

    /**
     * 更新视频热度
     * @param heatOfVideo
     */
    void updateVideoHeatOfVideo(Integer videoMainId , Double heatOfVideo);

    /**
     * 通过 wxUserId、teacherId和pageInfo 获得教师主页的视频列表
     * @param wxUserInfo
     * @param teacherId
     * @param pageInfo
     * @return
     */
    List<Video_main> getPublicVideoListbyTeacherId(Integer wxUserInfo,Integer teacherId,PageInfo pageInfo);

    /**
     * 通过 wxUserId，teacherId和pageInfo 获得school级别以上的视频
     * @param wxUserInfo
     * @param teacherId
     * @param pageInfo
     * @return
     */
    List<Video_main> getSchoolVideoListByTeacherId(Integer wxUserInfo,Integer teacherId,PageInfo pageInfo);

    /**
     * 通过 episodeId 和 videoMainId 查找视频信息
     * @param episodeId
     * @param videoMainId
     * @return
     */
    Video getVideoByEpisodeId_VideoMainId(Integer episodeId,Integer videoMainId);

    /**
     * 通过 wxUserId , pageInfo 获得视频点赞列表
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
