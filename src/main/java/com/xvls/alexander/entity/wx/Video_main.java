package com.xvls.alexander.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video_main {
    private Integer videoMainId;//主页ID
    private Integer teacherId;//教师ID
    private String videoMainTitle;//主页标题
    private String summary;//简介
    private Integer playNum;//播放量
    private Integer goodNum;//点赞数
    private Integer collectionNum;//收藏数
    private Date videoDate;//日期
    private Integer commentNum;//评论数
    private Boolean commentStatus;//评论状态
    private int videoLength;//视频总长度
    private String publicStatus;//播放等级
    private String verifyStatus;//审核状态
    private String mainPage;//封面

    private String nickName;//教师名称

    private List<Video_episode> video_episodeList;//视频集数信息
}
