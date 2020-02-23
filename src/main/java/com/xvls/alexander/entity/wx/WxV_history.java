package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 微信历史记录列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxV_history {

    private Integer videoMainId;//主页ID
    private Integer teacherId;//教师ID
    @TableField(exist = false)
    private String userName;//教师姓名
    private String videoMainTitle;//主页标题
    private Integer playNum;//播放量
    private Integer goodNum;//点赞数
    private Integer collectionNum;//收藏数
    private Integer commentNum;//评论数
    private String mainPage;//封面

    private Integer episodeId;//集数
    private Date watchDate;//观看时间
}
