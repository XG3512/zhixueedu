package com.xvls.alexander.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成绩
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxGrade {

    private Integer scId;
    private String courseName;//课程名称
    private Float credit;//学分
    private Integer score;//成绩
    private Float gradePoint;//绩点
    private String semester;//学期
}
