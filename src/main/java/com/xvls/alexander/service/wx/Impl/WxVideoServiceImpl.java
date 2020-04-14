package com.xvls.alexander.service.wx.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.VideoMapper;
import com.xvls.alexander.dao.WxVideoMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Label;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.wx.WxToolBarService;
import com.xvls.alexander.service.wx.WxVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WxVideoServiceImpl implements WxVideoService {

    @Autowired
    WxVideoMapper wxVideoMapper;
    @Autowired
    VideoMapper videoMapper;

    /**
     * 获得视频列表信息
     * @return
     */
    @Override
    public List<Video_main> getPublicVideoList(Integer wxUserId) {
        return wxVideoMapper.getPublicVideoList(wxUserId);
    }

    /**
     * 通过 schoolId,wxUserId,pageInfo 获得学校的视频信息，在学校主页面
     * @param schoolId
     * @param pageInfo
     * @param wxUserId
     * @return
     */
    @Override
    public List<Video_main> getPublicVideoListBySchoolId(Integer schoolId,Integer wxUserId,PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return wxVideoMapper.getPublicVideoListBySchoolId(schoolId,wxUserId,new PageInfo(pageNum,pageSize));
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
     * 通过 分页,标签,wxUserId 获得视频列表
     * @param pageInfo
     * @param labelId
     * @return
     */
    @Override
    public List<Video_main> getPublicVideoListByLabel(PageInfo pageInfo, Integer labelId , Integer wxUserId) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return wxVideoMapper.getPublicVideoListByLabel(new PageInfo(pageNum,pageSize),labelId,wxUserId);
    }

    /**
     * 通过 pageInfo , wxUserId 获得精选页面的视频列表（按照热度排序）
     * @param pageInfo
     * @param wxUserId
     * @return
     */
    @Override
    public List<Video_main> getMainPagePublicVideoList(PageInfo pageInfo, Integer wxUserId) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return wxVideoMapper.getMainPagePublicVideoList(new PageInfo(pageNum,pageSize),wxUserId);
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
     * 更新视频评论总数
     * @param videoMainId
     */
    @Override
    public void updateVideoCommentNum(Integer videoMainId) {
        wxVideoMapper.updateVideoCommentNum(videoMainId);
    }


    /**
     * 计算并更新视频热度
     * @param video_main
     */
    @Override
    public void updateVideoHeatOfVideo(Video_main video_main) {
        Double result = Calculate(video_main);
        //System.out.println(result);
        wxVideoMapper.updateVideoHeatOfVideo(video_main.getVideoMainId(),result);
    }

    /**
     * 通过 wxUserId，teacherId，publicStatus，pageInfo 获得publicStatus级别的视频
     * @param wxUserInfo
     * @param teacherId
     * @param pageInfo
     * @param publicStatus
     * @return
     */
    @Override
    public List<Video_main> getVideoListByTeacherId(Integer wxUserInfo, Integer teacherId, PageInfo pageInfo,String publicStatus) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageInfo.setPageNum((pageNum-1)*pageSize);
        return wxVideoMapper.getVideoListbyTeacherId(wxUserInfo,teacherId,pageInfo,publicStatus);
    }

    /**
     * 通过 episodeId 和 videoMainId 查找视频信息
     * @param episodeId
     * @param videoMainId
     * @return
     */
    @Override
    public Video getVideoByEpisodeId_VideoMainId(Integer episodeId, Integer videoMainId) {
        return wxVideoMapper.getVideoByEpisodeId_VideoMainId(episodeId,videoMainId);
    }

    /**
     * 更新视频地址
     * @param video
     */
    @Override
    public void updateVideoById(Video video) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id",video.getVideoId());
        video.update(queryWrapper);
    }

    /**
     * 插入视频
     * @param video
     * @return
     */
    @Override
    public Integer insertVideo(Video video) {
        int insert = videoMapper.insert(video);
        return video.getVideoId();
    }

    /**
     * 通过 wxUserId , pageInfo 获得视频点赞列表
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    @Override
    public List<Video_main> getVideoGoodList(Integer wxUserId, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return wxVideoMapper.getVideoGoodList(wxUserId,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 wxUserId , pageInfo 获得视频 收藏 列表
     * @param wxUserId
     * @param pageInfo
     * @return
     */
    @Override
    public List<Video_main> getVideoCollectionList(Integer wxUserId, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return wxVideoMapper.getVideoCollectionList(wxUserId,new PageInfo(pageNum,pageSize));
    }


    public Double Calculate(Video_main video_main){
        Double result = null;

        Long videoDate = (video_main.getVideoDate().getTime()/3600000);
        Double playNum = video_main.getPlayNum().doubleValue();
        Double goodNum = video_main.getGoodNum().doubleValue();
        Double collectionNum = video_main.getCollectionNum().doubleValue();
        Double commentNum = video_main.getCommentNum().doubleValue();
        Long date = new Date().getTime()/3600000;

        result = (playNum*0.0769+commentNum*0.1538+goodNum*0.3077+collectionNum*0.4615)*1000+100.0;//1,2,4,6
        Double temp;
        if((date-videoDate)>=168){
            temp = Math.pow(168+2,1.36);
        }else{
            temp = Math.pow(date-videoDate+2,1.36);
        }
        //Double temp = Math.pow(Math.E,0.01279*(date-videoDate));
        result = result/temp;
        return result;
    }


}
