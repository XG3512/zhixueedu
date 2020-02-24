package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信端 教师
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends Model<Teacher> {

    private Integer userId;
    private Integer departmentId;
    private String userName;
    private String icon;
}
