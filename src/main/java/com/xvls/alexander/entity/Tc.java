package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教学
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tc")
public class Tc {

    @TableId(type = IdType.AUTO)
    private Integer tcId;
    private Integer userId;

    private Integer courseId;
    @TableField(exist = false)
    private String courseName;

    private Integer schoolId;
    @TableField(exist = false)
    private String schoolName;

    private Integer classId;
    @TableField(exist = false)
    private String className;

    private String semester;
}
