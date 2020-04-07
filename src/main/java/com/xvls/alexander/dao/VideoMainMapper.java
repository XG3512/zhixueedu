package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.VideoMain;
import com.xvls.alexander.entity.wx.Video_episode;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
     * @param editTime
     */
    void updateMainPage(Integer videoMainId,String mainPage,Date editTime);

    /**
     * 通过 videoMainId,videoMainTitle,summary 修改视频主页信息
     * @param videoMainId
     * @param videoMainTitle
     * @param summary
     * @param editTime
     */
    void updateMainPageInfo(Integer videoMainId, String videoMainTitle, String summary, Date editTime);

    /**
     * 通过 videoId，videoTitle，episodeId 修改视频信息
     * @param videoId
     * @param videoTitle
     * @param episodeId
     */
    void updateVideoInfo(Integer videoId, String videoTitle, Integer episodeId);

    /**
     * 通过 videoId,edtiTime 修改视频主页编辑时间
     * @param videoId
     * @param editTime
     */
    void updateVideoMainEditTimeByVideoId(Integer videoId,Date editTime);

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

    /******************************视频审核************************************/
    /**
     * 通过 userId、pageInfo 获得视频审核列表
     * @param userId
     * @param verifyStatus
     * @param pageInfo
     * @return
     */
    List<VideoMain> getVerifyVideoMainList(Integer userId,String verifyStatus,PageInfo pageInfo);

    /**
     * 通过 userId,verifyStatus 获得视频主页审核的数目
     * @param userId
     * @param verifyStatus
     * @return
     */
    Integer getVerifyVideoMainCount(Integer userId,String verifyStatus);

    /**
     * 通过 videoMainId,verifyStatus,pageInfo 获得视频集数列表
     * @param videoMainId
     * @param verifyStatus
     * @param pageInfo
     * @return
     */
    List<Video_episode> getVerifyVideoEpisodeList(Integer videoMainId,String verifyStatus,PageInfo pageInfo);

    /**
     * 通过 videoMainId,verifyStatus 获得视频审核的数目
     * @param videoMainId
     * @param verifyStatus
     * @return
     */
    Integer getVerifyVideoEpisodeCount(Integer videoMainId,String verifyStatus);

    /**
     * 通过 videoMainId，verifyStatus 修改视频主页审核状态
     * @param videoMainId
     * @param verifyStatus
     */
    void editVideoMainVerifyStatus(Integer videoMainId,String verifyStatus);

    /**
     * 通过 videoId，verifyStatus 修改 视频 审核状态
     * @param videoId
     * @param verifyStatus
     */
    void editVideoEpisodeVerifyStatus(Integer videoId,String verifyStatus);
}
