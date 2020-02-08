package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("notice")
public class Notice extends Model<Notice> {

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Integer noticeId;//通知ID
    private Integer schoolId;//学校ID
    private String title;//标题
    private String content;//内容
    private String noticeText;//文字信息
    private Integer readNum;//阅读量
    private Date noticeTime;//日期
    private Integer goodNum;//点赞数
    private Boolean commentStatus;//评论状态
    private Integer commentNum;//评论数

    private String readNumText;//阅读量
    private String goodNumText;//点赞数
    private String commentNumText;//评论数
}
