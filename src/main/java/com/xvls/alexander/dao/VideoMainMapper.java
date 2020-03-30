package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.VideoMain;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoMainMapper extends BaseMapper<VideoMain> {

    /**
     * 通过 teacherId，pageInfo 获得视频列表
     * @param teacherId
     * @param pageInfo
     * @return
     */
    List<VideoMain> getVideoListByTeacherId(Integer teacherId, PageInfo pageInfo);

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
     * 通过 userIdList 批量删除视频主页
     * @param userIdList
     */
    void deleteVideoMainPageByUserIdList(List<Integer> userIdList);
    /**
     * 通过 userIdList 批量删除视频
     * @param userIdList
     */
    void deleteVideoByUserIdList(List<Integer> userIdList);
}
