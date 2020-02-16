package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频标签
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Label extends Model<Label> {

    @TableId(type = IdType.AUTO)
    private Integer labelId;//标签ID
    private String labelName;//标签名称
}
