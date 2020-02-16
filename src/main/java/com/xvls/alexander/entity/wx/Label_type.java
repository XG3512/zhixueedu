package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 标签类别
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("label_type")
public class Label_type extends Model<Label_type> {

    @TableId(type = IdType.AUTO)
    private Integer labelTypeId;//标签类型ID
    private String labelTypeName;//标签类型名称
    private Integer labelTypeOrder;//标签类型顺序

    @TableField(exist = false)
    List<Label> labelList;
}
