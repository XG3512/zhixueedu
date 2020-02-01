package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.google.common.collect.Sets;
import com.xvls.alexander.entity.Permission;
import com.xvls.alexander.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * 微信用户
 */
@TableName("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxUserInfo extends Model<WxUserInfo> implements Serializable {

    @TableId(type = IdType.AUTO)
    private int userId;

    private String nickname;
    private String userNum;
    private String password;
    private int schoolId;
    private int departmentId;
    private int majorId;
    private int classId;
    private String userName;
    private String sex;
    private String nation;
    private int grade;
    private String idCard;
    private String phone;
    private String mail;
    private String address;
    private String salt;
    private String openid;
    private String sessionKey;
    private String Icon;
    private String city;
    private String country;
    private String province;
    private int wxstatus;

    @TableField(exist = false)//该字段在数据库表中不存在
    private Set<Role> roleList = Sets.newHashSet();//角色列表

    @TableField(exist = false)
    private Set<Permission> permissions = Sets.newHashSet();//权限列表



}
