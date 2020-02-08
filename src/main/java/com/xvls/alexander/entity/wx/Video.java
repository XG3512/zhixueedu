package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// TODO: 2020/2/8 播放量、点赞数 收藏数的字符化，以及数据库的修改

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("video")
public class Video extends Model<Video> {

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Integer videoId;//视频id
    private Integer teacherId;//教师Id
    private String videoTitle;//简介
    private Integer summary;//播放量
    private Integer playNum;//点赞数
    private Integer goodNum;//收藏数
    private Date videoDate;//日期
    private Integer videoMainId;//主页ID
    private Integer episodeId;//集数
    private Integer commentNum;//评论数
    private boolean commentStatus;//评论状态
    private String videoLength;//视频长度
    private String videoSize;//视频大小
    private String publicStatus;//播放等级
    private String verifyStatus;//审核状态
    private String videoSource;//视频地址

    /**--------------------------------**/
    private String summaryText;//播放量
    private String playNumText;//点赞数
    private String goodNumText;//收藏数
}
