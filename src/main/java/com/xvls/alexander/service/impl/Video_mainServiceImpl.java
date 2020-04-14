package com.xvls.alexander.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.UsersMapper;
import com.xvls.alexander.dao.VideoMainMapper;
import com.xvls.alexander.dao.WxVideoMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.VideoMain;
import com.xvls.alexander.entity.wx.Users;
import com.xvls.alexander.entity.wx.Video_episode;
import com.xvls.alexander.entity.wx.Video_main;
import com.xvls.alexander.service.Video_mainService;
import com.xvls.alexander.service.wx.WxToolBarService;
import com.xvls.alexander.service.wx.WxVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class Video_mainServiceImpl implements Video_mainService {

    @Autowired
    WxVideoMapper wxVideoMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    WxVideoService wxVideoService;
    @Autowired
    VideoMainMapper videoMainMapper;
    @Autowired
    WxToolBarService wxToolBarService;

    /**
     * 上传 视频主页 信息
     * @param video_main
     */
    @Override
    public Integer uploadVideoMain(Video_main video_main) {
        Users users = usersMapper.selectById(video_main.getTeacherId());
        video_main.setSchoolId(users.getSchoolId());
        video_main.setHeatOfVideo(Calculate(video_main));
        int i = wxVideoMapper.insert(video_main);
        return video_main.getVideoMainId();
    }

    /**
     * 通过 teacherId，pageInfo 获得视频列表
     * @param teacherId
     * @param pageInfo
     * @return
     */
    @Override
    public List<VideoMain> getVideoListByTeacherId(Integer teacherId, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return videoMainMapper.getVideoListByTeacherId(teacherId,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 teacherId 获得视频主页总数目
     * @param teacherId
     * @return
     */
    @Override
    public Integer getVideoMainPageCount(Integer teacherId) {
        QueryWrapper<VideoMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        return videoMainMapper.selectCount(queryWrapper);
    }


    /**
     * 通过 videoMainId 获得视频详细信息
     * @param videoMainId
     * @return
     */
    @Override
    public VideoMain getVideoMainInfoById(Integer videoMainId) {
        return videoMainMapper.getVideoMainInfoById(videoMainId);
    }

    /**
     * 通过 teacherId,content,pageInfo 进行模糊查询
     * @param teacherId
     * @param content
     * @param pageInfo
     * @return
     */
    @Override
    public List<VideoMain> getVideoMainListByContent(Integer teacherId, String content, PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        content = "%"+content+"%";
        return videoMainMapper.getVideoMainListByContent(teacherId,content,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 teacherId,content,pageInfo 获得模糊查询视频总数
     * @param teacherId
     * @param content
     * @return
     */
    @Override
    public Integer getVideoMainPageCountByContent(Integer teacherId, String content) {
        content = "%"+content+"%";
        return videoMainMapper.getVideoMainPageCountByContent(teacherId,content);
    }

    /**
     * 更新视频主页图片
     * @param videoMainId
     * @param mainPage
     * @param editTime
     */
    @Override
    public void updateMainPage(Integer videoMainId, String mainPage,Date editTime) {
        videoMainMapper.updateMainPage(videoMainId,mainPage,editTime);
    }

    /**
     * 通过 videoMainId,videoMainTitle,summary 修改视频主页信息
     * @param videoMainId
     * @param videoMainTitle
     * @param summary
     * @param editTime
     */
    @Override
    public void updateMainPageInfo(Integer videoMainId, String videoMainTitle, String summary, Date editTime) {
        videoMainMapper.updateMainPageInfo(videoMainId,videoMainTitle,summary,editTime);
    }

    /**
     * 通过 videoId，videoTitle，episodeId 修改视频信息
     * @param videoId
     * @param videoTitle
     * @param episodeId
     */
    @Override
    public void updateVideoInfo(Integer videoId, String videoTitle, Integer episodeId) {
        videoMainMapper.updateVideoInfo(videoId,videoTitle,episodeId);
        videoMainMapper.updateVideoMainEditTimeByVideoId(videoId,new Date());
    }

    /**
     * 通过 videoMainIdList 批量删除视频主页信息和相关视频
     * @param videoMainIdList
     */
    @Override
    public void deleteVideoMain(List<Integer> videoMainIdList) {
        videoMainMapper.deleteVideoMain(videoMainIdList);
        this.deleteVideoByMainId(videoMainIdList);
        wxToolBarService.deleteGoods("V",videoMainIdList);
        wxToolBarService.deleteCollections("V",videoMainIdList);
    }

    /**
     * 通过 videoMainIdList 批量删除video
     * @param videoMainIdList
     */
    @Override
    public void deleteVideoByMainId(List<Integer> videoMainIdList) {
        videoMainMapper.deleteVideoByMainId(videoMainIdList);
    }

    /**
     * 通过 videoIdList 批量删除视频
     * @param videoIdList
     */
    @Override
    public void deleteVideo(List<Integer> videoIdList) {
        videoMainMapper.deleteVideo(videoIdList);
    }

    /**
     * 通过 userIdList 获得视频idList
     * @param userIdList
     * @return
     */
    @Override
    public List<Integer> getVideoMainIdList(List<Integer> userIdList) {
        return videoMainMapper.getVideoMainIdList(userIdList);
    }

    /**
     * 通过 userIdList 批量删除视频主页
     * @param userIdList
     */
    @Override
    public void deleteVideoMainPageByUserIdList(List<Integer> userIdList) {
        List<Integer> videoMainIdList = videoMainMapper.getVideoMainIdList(userIdList);
        videoMainMapper.deleteVideoMainPageByUserIdList(userIdList);
        wxToolBarService.deleteCollections("V",videoMainIdList);
        wxToolBarService.deleteGoods("V",videoMainIdList);
    }

    /**
     * 通过 userIdList 批量删除视频
     * @param userIdList
     */
    @Override
    public void deleteVideoByUserIdList(List<Integer> userIdList) {
        videoMainMapper.deleteVideoByUserIdList(userIdList);
    }


    @Override
    public Double Calculate(Video_main video_main){
        Double result = null;

        Long videoDate = (video_main.getVideoDate().getTime()/3600000);
        Double playNum = video_main.getPlayNum().doubleValue();
        Double goodNum = video_main.getGoodNum().doubleValue();
        Double collectionNum = video_main.getCollectionNum().doubleValue();
        Double commentNum = video_main.getCommentNum().doubleValue();
        Long date = new Date().getTime()/3600000;

        result = (playNum*0.0769+commentNum*0.1538+goodNum*0.3077+collectionNum*0.4615)*1000+100.0;//1,2,4,6
        Double temp = Math.pow(date-videoDate+2,1.4);
        //Double temp = Math.pow(Math.E,0.01279*(date-videoDate));
        result = result/temp;
        return result;
    }

    /******************************视频审核************************************/
    /**
     * 通过 userId、pageInfo 获得视频审核列表
     * @param userId
     * @param verifyStatus
     * @param pageInfo
     * @return
     */
    @Override
    public List<VideoMain> getVerifyVideoMainList(Integer userId,String verifyStatus,PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return videoMainMapper.getVerifyVideoMainList(userId,verifyStatus,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 userId,verifyStatus 获得视频主页审核的数目
     * @param userId
     * @param verifyStatus
     * @return
     */
    @Override
    public Integer getVerifyVideoMainCount(Integer userId, String verifyStatus) {
        return videoMainMapper.getVerifyVideoMainCount(userId,verifyStatus);
    }

    /**
     * 通过 videoMainId,verifyStatus,pageInfo 获得视频集数列表
     * @param videoMainId
     * @param verifyStatus
     * @param pageInfo
     * @return
     */
    @Override
    public List<Video_episode> getVerifyVideoEpisodeList(Integer videoMainId,String verifyStatus,PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum=(pageNum-1)*pageSize;
        return videoMainMapper.getVerifyVideoEpisodeList(videoMainId,verifyStatus,new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 videoMainId,verifyStatus 获得视频审核的数目
     * @param videoMainId
     * @param verifyStatus
     * @return
     */
    @Override
    public Integer getVerifyVideoEpisodeCount(Integer videoMainId, String verifyStatus) {
        return videoMainMapper.getVerifyVideoEpisodeCount(videoMainId,verifyStatus);
    }

    @Override
    public Integer getAllVideoCountByVerifyStatus(String verifyStatus) {
        QueryWrapper<VideoMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("verify_status",verifyStatus);
        return videoMainMapper.selectCount(queryWrapper);
    }

    /**
     * 通过 videoMainId，verifyStatus 修改视频主页审核状态
     * @param videoMainId
     * @param verifyStatus
     */
    @Override
    public void editVideoMainVerifyStatus(Integer videoMainId, String verifyStatus) {
        videoMainMapper.editVideoMainVerifyStatus(videoMainId,verifyStatus);
    }

    /**
     * 通过 videoId，verifyStatus 修改 视频 审核状态
     * @param videoId
     * @param verifyStatus
     */
    @Override
    public void editVideoEpisodeVerifyStatus(Integer videoId, String verifyStatus) {
        videoMainMapper.editVideoEpisodeVerifyStatus(videoId,verifyStatus);
    }
}
