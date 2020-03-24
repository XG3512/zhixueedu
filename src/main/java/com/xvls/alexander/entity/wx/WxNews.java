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

/**
 *  教务通知信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("news")
public class WxNews extends Model<WxNews> {

    @TableId(type = IdType.AUTO)
    private Integer newsId;//通知Id
    private Integer schoolId;//学校id
    @TableField(exist = false)
    private String schoolName;//学校名称
    private Date newsDate;//通知日期
    private String title;//通知标题
    private String newsContent;//通知富文本内容
    private String newsText;//通知纯文字信息
    private Integer readNum;//阅读量
}
