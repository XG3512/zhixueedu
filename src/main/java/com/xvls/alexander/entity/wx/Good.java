package com.xvls.alexander.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 点赞信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Good {
    private String goodType;
    private Integer goodId;
    private Integer userId;
    private Date goodTime;
}
