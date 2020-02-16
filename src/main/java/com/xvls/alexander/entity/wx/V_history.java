package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 播放历史
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("v_history")
public class V_history extends Model<V_history> {

    private Integer videoMainId;//播放视频主页ID
    private Integer episodeId;//集数
    private Integer wxUserId;//用户ID
    private Date watchDate;//观看时间
    private Integer watchTo;//观看至
}
