package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 学校列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("school")
public class SchoolList extends Model<SchoolList> {

    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Integer schoolId;
    private String schoolName;//学校名称
    private String province;//省份
    private String introduction;

    @TableField(exist = false)
    private Date followSchoolDate;//关注时间
}
