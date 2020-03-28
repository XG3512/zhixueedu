package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xvls.alexander.entity.wx.Label;
import com.xvls.alexander.entity.wx.Video_episode;
import com.xvls.alexander.utils.CalculateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("video_main")
public class VideoMain extends Model<VideoMain> {
    @TableId(type = IdType.AUTO)
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
    private Double heatOfVideo;//视频热度
    private Integer schoolId;//学校Id

    /**
     * 视频标签
     */
    @TableField(exist = false)
    private List<Label> labelList;//视频标签列表

    @TableField(exist = false)
    private List<Video_episode> episodeListOfPass;//视频 通过 集数信息
    @TableField(exist = false)
    private List<Video_episode> episodeListOfVerify;//视频 审核中 集数信息
    @TableField(exist = false)
    private List<Video_episode> episodeListOfNotPass;//视频 未通过 集数信息
}
