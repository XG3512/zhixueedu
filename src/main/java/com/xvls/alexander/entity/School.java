package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class School extends Model<School> implements Serializable {

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private int schoolId;
    private String schoolNum;
    private String schoolName;
    @TableField(exist = false)//该字段不能被查到
    private String password;
    private String province;
    private String head;
    private int fansNum;
    private int influence;
    private int ranking;
    private String introduction;
    private String address;
    private String background;
}
