package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("video")
public class Video extends Model<Video> {

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Integer videoId;//视频id
    private Integer teacherId;//教师Id
    private String videoTitle;//简介
    private Date videoDate;//日期
    private Integer videoMainId;//主页ID
    private Integer episodeId;//集数
    private String videoLength;//视频长度
    private String videoSize;//视频大小
    private String publicStatus;//播放等级
    private String verifyStatus;//审核状态
    private String videoSource;//视频地址
}
