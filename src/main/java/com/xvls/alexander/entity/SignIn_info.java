package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 签到信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("signIn_info")
public class SignIn_info extends Model<SignIn_info> {

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Integer siiId;
    private Integer tcId;
    private Integer teacherId;
    private Date startTime;
    private Date endTime;
}
