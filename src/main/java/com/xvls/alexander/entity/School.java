package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xvls.alexander.utils.CalculateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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

    @TableField(exist = false)
    private Date followSchoolDate;//关注学校的时间


    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
        this.fansNumText= CalculateUtil.CalculateNum(fansNum);
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
        this.goodNumText= CalculateUtil.CalculateNum(goodNum);
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
        this.collectionNumText= CalculateUtil.CalculateNum(collectionNum);
    }

    public School(Integer schoolId, String schoolNum, String schoolName, String password, String province, String head, Integer fansNum, Integer goodNum, Integer ranking, String introduction, String address, String background, String englishName, Integer collectionNum) {
        this.schoolId = schoolId;
        this.schoolNum = schoolNum;
        this.schoolName = schoolName;
        this.password = password;
        this.province = province;
        this.head = head;
        this.fansNum = fansNum;
        this.goodNum = goodNum;
        this.ranking = ranking;
        this.introduction = introduction;
        this.address = address;
        this.background = background;
        this.englishName = englishName;
        this.collectionNum = collectionNum;
        this.fansNumText= CalculateUtil.CalculateNum(fansNum);
        this.goodNumText= CalculateUtil.CalculateNum(goodNum);
        this.collectionNumText= CalculateUtil.CalculateNum(collectionNum);
    }
}
