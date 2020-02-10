package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xvls.alexander.utils.CalculateUtil;
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

    @TableField(exist = false)
    private String readNumText;//阅读量
    @TableField(exist = false)
    private String goodNumText;//点赞数
    @TableField(exist = false)
    private String commentNumText;//评论数

    public Notice(Integer noticeId, Integer schoolId, String title, String content, String noticeText, Integer readNum, Date noticeTime, Integer goodNum, Boolean commentStatus, Integer commentNum) {
        this.noticeId = noticeId;
        this.schoolId = schoolId;
        this.title = title;
        this.content = content;
        this.noticeText = noticeText;
        this.readNum = readNum;
        this.noticeTime = noticeTime;
        this.goodNum = goodNum;
        this.commentStatus = commentStatus;
        this.commentNum = commentNum;
        this.readNumText = CalculateUtil.CalculateNum(readNum);
        this.goodNumText = CalculateUtil.CalculateNum(goodNum);
        this.commentNumText = CalculateUtil.CalculateNum(commentNum);
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
        this.readNumText = CalculateUtil.CalculateNum(readNum);
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
        this.goodNumText = CalculateUtil.CalculateNum(goodNum);
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
        this.commentNumText = CalculateUtil.CalculateNum(commentNum);
    }
}
