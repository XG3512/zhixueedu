package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 教师主页中的教师信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherMainPage {

    @TableId(type = IdType.AUTO)
    private Integer userId;//教师Id

    private String icon;//头像
    private String background;//背景

    private String userName;//教师名称
    private String motto;//座右铭

    private Integer fansNum;//粉丝数
    private Integer goodNum;//点赞数
    private Integer collectionNum;//收藏数

    @TableField(exist = false)
    private String schoolName;//学校名称

    @TableField(exist = false)
    private Date followTeacherDate;//关注时间
}
