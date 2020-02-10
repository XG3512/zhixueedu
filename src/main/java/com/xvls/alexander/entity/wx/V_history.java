package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class V_history extends Model<V_history> {

    @TableId(value = "v_history_id",type = IdType.INPUT)//用户输入ID
    private Integer vHistoryId;//播放视频主页ID
    private Integer episodeId;//集数
    private Integer userId;//用户ID
    private Date watchDate;//观看时间
    private Integer watchTo;//观看至
}
