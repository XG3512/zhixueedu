package com.xvls.alexander.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 视频集数信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video_episode {
    private Integer videoId;//视频ID
    private String videoTitle;//视频标题
    private int videoLength;//视频长度
    private String videoSize;//视频大小
    private Date videoDate;//视频日期
    private Integer episodeId;//视频集数ID
    private String verifyStatus;//审核状态
    private String videoSource;//视频资源地址
}
