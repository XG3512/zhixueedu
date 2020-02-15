package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xvls.alexander.entity.School;
import com.xvls.alexander.utils.CalculateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.sql.rowset.CachedRowSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article")
public class Article extends Model<Article> implements Serializable {

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Integer articleId;
    private Integer schoolId;
    private String title;
    private String content;
    private String articleText;
    private Integer readNum; //阅读量
    private Date articleTime;
    private Integer goodNum;
    private boolean commentStatus;//评论状态
    private Integer commentNum;//评论数
    private Integer collectionNum;//收藏数

    private School school;//学校信息

    @TableField(exist = false)
    private Date goodTime;//点赞时间
    @TableField(exist = false)
    private Date collectionDate;//收藏时间

    @TableField(exist = false)
    private List<ArticleImage> articleimageList;//动态图片列表

    @TableField(exist = false)
    private String readNumText;//阅读量文本
    @TableField(exist = false)
    private String goodNumText;//点赞文本
    @TableField(exist = false)
    private String commentNumText;//评论数文本
    @TableField(exist = false)
    private String collectionNumText;//收藏数文本

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

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
        this.collectionNumText = CalculateUtil.CalculateNum(collectionNum);
    }

    public Article(Integer articleId, Integer schoolId, String title, String content, String articleText, Integer readNum, Date articleTime, Integer goodNum, boolean commentStatus, Integer commentNum, School school) {
        this.articleId = articleId;
        this.schoolId = schoolId;
        this.title = title;
        this.content = content;
        this.articleText = articleText;
        this.readNum = readNum;
        this.articleTime = articleTime;
        this.goodNum = goodNum;
        this.commentStatus = commentStatus;
        this.commentNum = commentNum;
        this.school = school;
        this.readNumText = CalculateUtil.CalculateNum(readNum);
        this.goodNumText = CalculateUtil.CalculateNum(goodNum);
        this.commentNumText = CalculateUtil.CalculateNum(commentNum);
        this.collectionNumText = CalculateUtil.CalculateNum(collectionNum);
    }
}
