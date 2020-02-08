package com.xvls.alexander.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("file_download")
public class File_download extends Model<File_download> implements Serializable {


    @TableId(type = IdType.AUTO)//在这里设置id的自增长，否则id将不会自增长且为随机数
    private Integer fileDownloadId;

    private String fileHash;//文件标识
    //@TableField("file_name")
    private String fileName;//文件名称
    //@TableField("file_url")
    private String fileUrl; //文件地址
    //@TableField("file_type")
    private String fileType;//文件类型
    //@TableField("file_size")
    private String fileSize;//文件大小
    //@TableField("file_date")
    private Date fileDate;//上传日期
    //@TableField("source")
    private String source;//文件来源
    //@TableField("file_status")
    private boolean fileStatus;//文件状态

    private String name;
}
