package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("v_label")
public class V_label extends Model<V_label> {

    private Integer videoMainId;
    private Integer labelId;
}
