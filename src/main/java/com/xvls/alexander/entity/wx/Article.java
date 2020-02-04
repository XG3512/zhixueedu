package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xvls.alexander.entity.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends Model<Article> implements Serializable {

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private int articleId;
    private int schoolId;
    private String title;
    private String content;
    private String articleText;
    private int readNum;
    private Date articleTime;
    private int goodNum;
    private boolean commentStatus;//评论状态
    private int commentNum;//评论数

    private School school;//学校信息

}
