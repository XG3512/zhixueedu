package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信主页的轮播图
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("home_rotation")
public class WxHomeRotation {

    @TableId(type = IdType.AUTO)
    private Integer homeRotationId;
    private String belongType;
    private Integer belongId;
    private String source;
}
