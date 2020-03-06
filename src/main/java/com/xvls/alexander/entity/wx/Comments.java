package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comments")
public class Comments extends Model<Comments> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer commentId;//评论Id
    private Integer wxUserId;//用户ID
    @TableField(exist = false)
    private String avatarUrl;//用户头像
    @TableField(exist = false)
    private String nickname;//用户名称
    private String belongType;//所属类型
    private Integer belongId;//所属id
    private String vcContent;//评论内容
    private Integer goodNum;//点赞数
    private Date vcDate;//评论时间
    private Integer parentVcId;//父类
    private String parentName;

    @TableField(exist = false)
    private Integer membersCount;//子评论个数

    @TableField(exist = false)
    private List<Comments> members;//子评论
}
