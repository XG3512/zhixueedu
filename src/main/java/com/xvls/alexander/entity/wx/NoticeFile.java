package com.xvls.alexander.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 公告的文件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFile {

    private String fileUrl;//文件url
    private String name;//文件名称
    private String fileType;//文件类型
    private String fileSize;//文件大小
}
