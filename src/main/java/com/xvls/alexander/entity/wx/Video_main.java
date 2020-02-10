package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xvls.alexander.utils.CalculateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 通过set、get方法和构造函数来解决 数量的字符问题
 */
@Data
@NoArgsConstructor
@TableName("video_main")
public class Video_main extends Model<Video_main> {
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

    @TableField(exist = false)
    private String playNumText;//播放量文本
    @TableField(exist = false)
    private String goodNumText;//点赞数文本
    @TableField(exist = false)
    private String collectionNumText;//收藏数文本
    @TableField(exist = false)
    private String commentNumText;//评论数文本

    @TableField(exist = false)
    private String nickName;//教师名称

    @TableField(exist = false)
    private List<Video_episode> video_episodeList;//视频集数信息

    public Video_main(Integer videoMainId, Integer teacherId, String videoMainTitle, String summary, Integer playNum, Integer goodNum, Integer collectionNum, Date videoDate, Integer commentNum, Boolean commentStatus, int videoLength, String publicStatus, String verifyStatus, String mainPage,  String nickName, List<Video_episode> video_episodeList) {
        this.videoMainId = videoMainId;
        this.teacherId = teacherId;
        this.videoMainTitle = videoMainTitle;
        this.summary = summary;
        this.playNum = playNum;
        this.goodNum = goodNum;
        this.collectionNum = collectionNum;
        this.videoDate = videoDate;
        this.commentNum = commentNum;
        this.commentStatus = commentStatus;
        this.videoLength = videoLength;
        this.publicStatus = publicStatus;
        this.verifyStatus = verifyStatus;
        this.mainPage = mainPage;
        this.playNumText = CalculateUtil.CalculateNum(playNum);
        this.goodNumText = CalculateUtil.CalculateNum(goodNum);
        this.collectionNumText = CalculateUtil.CalculateNum(collectionNum);
        this.commentNumText = CalculateUtil.CalculateNum(commentNum);
        this.nickName = nickName;
        this.video_episodeList = video_episodeList;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
        this.playNumText=CalculateUtil.CalculateNum(playNum);
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
        this.goodNumText=CalculateUtil.CalculateNum(goodNum);
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
        this.collectionNumText=CalculateUtil.CalculateNum(collectionNum);
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
        this.commentNumText=CalculateUtil.CalculateNum(commentNum);
    }
}
