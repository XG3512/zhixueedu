package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 微信端 学院
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxDepartment {

    @TableId(type = IdType.AUTO)
    private Integer departmentId;//学院Id
    private String departmentName;//学院名称

    @TableField(exist = false)
    private List<Teacher> teacherList;//教师列表
}
