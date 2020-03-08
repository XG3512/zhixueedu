package com.xvls.alexander.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 签到列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignIn_infoList {

    private Integer siiId;
    private Integer tcId;

    private Integer teacherId;
    private String userName;//教师姓名

    private Date startTime;
    private Date endTime;

    private String courseName;
    private String className;
    private String semester;

    private Integer schoolId;
    private String schoolName;
}
