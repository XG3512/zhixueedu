package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 考试
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @TableId(type = IdType.AUTO)
    private Integer examId;//考试ID
    private Integer tcId;//考试课程ID
    //private Integer classroomId;//考试教室
    @TableField(exist = false)
    private String classroomCode;//教室编号
    private Date examTime;//考试时间
    //private Integer userId;//监考老师ID
    @TableField(exist = false)
    private String teacherName;//监考老师姓名
    private Integer stuNum;//考试人数
    private Boolean examStatus;//是否完成考试
}
