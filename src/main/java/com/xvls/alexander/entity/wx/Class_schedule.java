package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生课表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class_schedule {

    @TableId(type = IdType.AUTO)
    private Integer cscheduleId;//课表Id
    private Integer userId;//用户Id
    private Integer tcId;//授课Id
    @TableField(exist = false)
    private String teacherName;//教师姓名
    @TableField(exist = false)
    private String courseName;//课程名称
    private Integer classroomId;//教室Id
    @TableField(exist = false)
    private String classroomCode;//教室编号
    private Integer dayTime;//哪天上课
    private Integer startTime;//开始上课时间
    private Integer classLength;//上课时长
}
