package com.xvls.alexander.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学期平均绩点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxGradeAvgPoint {

    private String semester;
    private Float avgPoint;
}
