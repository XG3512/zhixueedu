package com.xvls.alexander.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 动态图片信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleImage {

    private String file_url;//图片来源
    private String name;//文件名称
    private Boolean islongimage;//是否是长图

}
