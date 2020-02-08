package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("school")
public class School extends Model<School> implements Serializable {

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Integer schoolId;
    private String schoolNum;//编号
    private String schoolName;//学校名称
    @TableField(exist = false)//该字段不能被查到
    private String password;//密码
    private String province;//省份
    private String head;//学校头像
    private Integer fansNum;//粉丝数
    private Integer goodNum;//点赞数
    private Integer ranking;//学校排名
    private String introduction;//学校简介
    private String address;//学校地址
    private String background;//主页背景
    private String englishName;//英文名称
    private Integer collectionNum;//获得收藏数

    @TableField(exist = false)
    private String fansNumText;//粉丝数
    @TableField(exist = false)
    private String goodNumText;//点赞数
    @TableField(exist = false)
    private String collectionNumText;//获得收藏数
}
