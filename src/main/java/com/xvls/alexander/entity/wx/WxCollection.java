package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 收藏
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("collection")
public class WxCollection extends Model<WxCollection> {

    private String collectionType;//收藏内容类型
    private Integer collectionId;//收藏内容ID
    private Integer wxUserId;//微信用户ID
    private Date collectionDate;//收藏时间
    private Integer groupId;//收藏分组ID
}
