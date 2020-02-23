package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频精选 轮播图
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("video_rotation")
public class WxVideoRotation extends Model<WxVideoRotation> {

    @TableId(type = IdType.AUTO)
    private Integer videoRotationId;//主页轮播图Id
    private Integer videoMainId;//视频主页ID
    private String source;//轮播图图片
}
