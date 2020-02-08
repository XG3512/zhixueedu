package com.xvls.alexander.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments implements Serializable {

    private Integer commentId;//评论Id
    private Integer userId;//用户ID
    private String belongType;//所属类型
    private Integer belongId;//所属id
    private String vcContent;//评论内容
    private Integer goodNum;//点赞数
    private Date vcDate;//评论时间
    private Integer parentVcId;//父类

    private List<Comments> members;//子评论
}
