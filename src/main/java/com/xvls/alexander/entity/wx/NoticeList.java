package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xvls.alexander.utils.CalculateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("notice")
// TODO: 2020/2/10  阅读量、点赞数、评论数的字符化
public class NoticeList {
    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Integer noticeId;//通知ID
    private Integer schoolId;//学校ID
    private String title;//标题
    private Integer readNum;//阅读量
    private Date noticeTime;//日期
    private Integer goodNum;//点赞数
    private Boolean commentStatus;//评论状态
    private Integer commentNum;//评论数

    @TableField(exist = false)
    private String readNumText;
    @TableField(exist = false)
    private String goodNumText;
    @TableField(exist = false)
    private String commentNumText;

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

    public NoticeList(Integer noticeId, Integer schoolId, String title, Integer readNum, Date noticeTime, Integer goodNum, Boolean commentStatus, Integer commentNum) {
        this.noticeId = noticeId;
        this.schoolId = schoolId;
        this.title = title;
        this.readNum = readNum;
        this.noticeTime = noticeTime;
        this.goodNum = goodNum;
        this.commentStatus = commentStatus;
        this.commentNum = commentNum;
        this.readNumText = CalculateUtil.CalculateNum(readNum);
        this.goodNumText = CalculateUtil.CalculateNum(goodNum);
        this.commentNumText = CalculateUtil.CalculateNum(commentNum);
    }
}
