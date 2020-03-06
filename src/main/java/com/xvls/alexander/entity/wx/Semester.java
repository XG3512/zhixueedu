package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学期
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("semester")
public class Semester extends Model<Semester> {

    private String semesterCode;
    private Integer semesterYear;
    private Integer term;
}
