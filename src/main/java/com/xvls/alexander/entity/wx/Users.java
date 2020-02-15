package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.google.common.collect.Sets;
import com.xvls.alexander.entity.Permission;
import com.xvls.alexander.entity.Role;
import com.xvls.alexander.utils.CalculateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * 微信用户
 */
@TableName("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users extends Model<Users> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String userNum;
    private String password;
    private Integer schoolId;
    private Integer departmentId;
    private Integer majorId;
    private Integer classId;
    private String userName;
    private String sex;
    private String nation;
    private Integer grade;
    private String idCard;
    private String phone;
    private String mail;
    private String address;
    private String salt;
    private Integer wxUserId;

    @TableField(exist = false)//该字段在数据库表中不存在
    private Set<Role> roleList = Sets.newHashSet();//角色列表

    @TableField(exist = false)
    private Set<Permission> permissions = Sets.newHashSet();//权限列表

    @TableField(exist = false)
    private Integer fansNum;//粉丝数
    @TableField(exist = false)
    private Integer goodNum;//点赞数
    @TableField(exist = false)
    private Integer collectionNum;//收藏数

    @TableField(exist = false)
    private String fansNumText;//粉丝数文本
    @TableField(exist = false)
    private String goodNumText;//点赞数文本
    @TableField(exist = false)
    private String collectionNumText;//收藏数文本


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
}
