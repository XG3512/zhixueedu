package com.xvls.alexander.service;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.VideoMain;
import com.xvls.alexander.entity.wx.Video_main;

import java.util.List;

/**
 * 后台管理端的视频Service
 */
public interface Video_mainService {

    /**
     * 上传 视频主页 信息
     * @param video_main
     */
    Integer uploadVideoMain(Video_main video_main);

    /**
     * 通过 teacherId，pageInfo 获得视频列表
     * @param teacherId
     * @param pageInfo
     * @return
     */
    List<VideoMain> getVideoListByTeacherId(Integer teacherId, PageInfo pageInfo);

    /**
     * 通过 teacherId 获得视频主页总数目
     * @param teacherId
     * @return
     */
    Integer getVideoMainPageCount(Integer teacherId);

    /**
     * 通过 videoMainId 获得视频详细信息
     * @param videoMainId
     * @return
     */
    VideoMain getVideoMainInfoById(Integer videoMainId);

    /**
     * 通过 teacherId,content,pageInfo 进行模糊查询
     * @param teacherId
     * @param content
     * @param pageInfo
     * @return
     */
    List<VideoMain> getVideoMainListByContent(Integer teacherId,String content,PageInfo pageInfo);

    /**
     * 通过 teacherId,content,pageInfo 获得模糊查询视频总数
     * @param teacherId
     * @param content
     * @return
     */
    Integer getVideoMainPageCountByContent(Integer teacherId,String content);

    /**
     * 更新视频主页图片
     * @param videoMainId
     * @param mainPage
     */
    void updateMainPage(Integer videoMainId,String mainPage);

    /**
     * 通过 videoMainId,videoMainTitle,summary 修改视频主页信息
     * @param videoMainId
     * @param videoMainTitle
     * @param summary
     */
    void updateMainPageInfo(Integer videoMainId,String videoMainTitle,String summary);

    /**
     * 通过 videoId，videoTitle，episodeId 修改视频信息
     * @param videoId
     * @param videoTitle
     * @param episodeId
     */
    void updateVideoInfo(Integer videoId, String videoTitle, Integer episodeId);

    /**
     * 通过 videoMainIdList 批量删除视频主页信息和相关视频
     * @param videoMainIdList
     */
    void deleteVideoMain(List<Integer> videoMainIdList);

    /**
     * 通过 videoMainIdList 批量删除video
     * @param videoMainIdList
     */
    void deleteVideoByMainId(List<Integer> videoMainIdList);

    /**
     * 通过 videoIdList 批量删除视频
     * @param videoIdList
     */
    void deleteVideo(List<Integer> videoIdList);

    /**
     * 计算热度
     * @param video_main
     * @return
     */
    Double Calculate(Video_main video_main);
}
