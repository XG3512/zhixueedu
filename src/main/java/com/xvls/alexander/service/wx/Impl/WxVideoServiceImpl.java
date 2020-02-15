package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxVideoMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.wx.WxVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WxVideoServiceImpl implements WxVideoService {

    @Autowired
    WxVideoMapper wxVideoMapper;

    /**
     * 获得视频列表信息
     * @return
     */
    @Override
    public List<Video_main> getPublicVideoList(Integer wxUserId) {
        return wxVideoMapper.getPublicVideoList(wxUserId);
    }

    /**
     * 通过schoolId获得学校的视频信息，在学校主页面
     * @param schoolId
     * @return
     */
    @Override
    public List<Video_main> getPublicVideoListBySchoolId(Integer schoolId,Integer wxUserId) {
        return wxVideoMapper.getPublicVideoListBySchoolId(schoolId,wxUserId);
    }

    /**
     * 通过videoMainId,episodeId,获取该集视频的详细信息
     * @param videoMainId
     * @param episodeId
     * @return
     */
    @Override
    public Video getVideoInfoById(Integer videoMainId, Integer episodeId) {
        return wxVideoMapper.getVideoInfoById(videoMainId,episodeId);
    }

    /**
     * 通过videoMainId获取该主页面的视频信息
     * @param videoMainId
     * @return
     */
    @Override
    public Video_main getVideoMainInfo(Integer videoMainId,Integer wxUserId) {
        return wxVideoMapper.getVideoMainInfo(videoMainId,wxUserId);
    }

    /**
     * 获得首页视频列表
     * @return
     */
    @Override
    public List<Video_main> getHomePageVideoList(Integer wxUserId) {
        Integer pageNum = 1;
        Integer pageSize = 6;

        PageInfo pageInfo = new PageInfo(pageNum,pageSize);
        pageInfo.setPageNum((pageNum-1)*pageSize);

        return wxVideoMapper.getHomePageVideoList(pageInfo);
    }

    /**
     * 视频点赞数增加
     * @param videoId
     */
    @Override
    public void addVideoGoodNum(Integer videoId) {

        wxVideoMapper.addVideoGoodNum(videoId);
    }

    /**
     * 视频评论数增加
     * @param videoId
     */
    @Override
    public void addVideoCommentNum(Integer videoId) {
        wxVideoMapper.addVideoCommentNum(videoId);
    }

    /**
     * 视频浏览量增加
     * @param videoId
     */
    @Override
    public void addVideoPlayNum(Integer videoId) {
        wxVideoMapper.addVideoPlayNum(videoId);
    }

    /**
     * 视频点赞数减少
     * @param videoId
     */
    @Override
    public void decreaseVideoGoodNum(Integer videoId) {
        wxVideoMapper.decreaseVideoGoodNum(videoId);
    }

    /**
     * 视频评论数减少
     * @param videoId
     */
    @Override
    public void decreaseVideoCommentNum(Integer videoId) {
        wxVideoMapper.decreaseVideoCommentNum(videoId);
    }

    /**
     * 视频收藏数增加
     * @param videoId
     */
    @Override
    public void addVideoCollectionNum(Integer videoId) {
        wxVideoMapper.addVideoCollectionNum(videoId);
    }

    /**
     * 视频收藏数量减少
     * @param videoId
     */
    @Override
    public void decreaseVideoCollectionNum(Integer videoId) {
        wxVideoMapper.decreaseVideoCollectionNum(videoId);
    }

    /**
     * 计算并更新视频热度
     * @param video_main
     */
    @Override
    public void updateVideoHeatOfVideo(Video_main video_main) {
        Double result = null;

        Long videoDate = (video_main.getVideoDate().getTime()/3600000);
        Double playNum = video_main.getPlayNum().doubleValue();
        Double goodNum = video_main.getGoodNum().doubleValue();
        Double collectionNum = video_main.getCollectionNum().doubleValue();
        Double commentNum = video_main.getCommentNum().doubleValue();
        Long date = new Date().getTime()/3600000;

        result = (playNum*0.0769+commentNum*0.1538+goodNum*0.3077+collectionNum*0.4615)*1000;
        Double temp = Math.pow(date-videoDate+2,1.4);
        result = result/temp;

        //System.out.println(result);
        wxVideoMapper.updateVideoHeatOfVideo(video_main.getVideoMainId(),result);
    }


}