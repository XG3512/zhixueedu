package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 省份
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("province")
public class Province extends Model<Province> {

    @TableId(type = IdType.AUTO)
    private Integer provinceId;
    private String provinceName;

    @TableField(exist = false)
    private List<SchoolList> schoolLists;
}
