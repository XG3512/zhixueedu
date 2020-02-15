package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 关注的老师
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("follow_teacher")
public class Follow_teacher extends Model<Follow_teacher> {
    private Integer wxUserId;
    private Integer teacherId;
    private Date followTeacherDate;
}
