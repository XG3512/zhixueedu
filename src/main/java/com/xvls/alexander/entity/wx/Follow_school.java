package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 关注的学校
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("follow_school")
public class Follow_school extends Model<Follow_school> {
    private Integer wxUserId;
    private Integer schoolId;
    private Date followSchoolDate;
}
