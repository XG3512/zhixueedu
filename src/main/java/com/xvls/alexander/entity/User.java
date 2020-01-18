package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@TableName("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class User extends Model<User> {//在MP中，开启AR非常简单，只需要将实体对象继承Model即可。

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Long id;

    @TableField(value = "uname")//指定数据库表中的字段名
    private String uName;

    @TableField(select = false)//查询时不返回该字段的值
    private String password;

    @TableField(exist = false)//该字段在数据库表中不存在
    private String mail;
}
