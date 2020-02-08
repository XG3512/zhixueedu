package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到人员
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("signIn_user")
public class SignIn_user extends Model<SignIn_user> implements Serializable {
    private Integer siiId;//签到信息id
    private Integer userId;//用户id
    private Date siTime;//签到时间
}
