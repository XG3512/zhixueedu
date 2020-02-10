package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 点赞信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("good")
public class Good extends Model<Good> {
    private String goodType;//点赞内容的类型，'A','V','N'
    private Integer goodId;//点赞内容的Id
    private Integer userId;//用户Id
    private Date goodTime;//点赞时间
}
