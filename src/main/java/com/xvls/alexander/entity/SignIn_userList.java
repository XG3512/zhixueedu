package com.xvls.alexander.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignIn_userList {

    private Integer siiId;//签到信息id
    private Integer userId;//用户id
    private String userName;
    private Integer schoolId;
    private String schoolName;
    private Integer classId;
    private String className;
    private Date siTime;//签到时间
}
