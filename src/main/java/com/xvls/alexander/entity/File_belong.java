package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("file_belong")
public class File_belong extends Model<File_belong> implements Serializable {

    private Integer userId;
    private String belongType;// V(视频) | A(文章)
    private Integer belongId;
    private String fileHash;//文件标识
    private String name;//原名称

    @TableField(exist = false)
    private String fileUrl;//文件地址
    @TableField(exist = false)
    private String fileType;//文件类型
    @TableField(exist = false)
    private String fileSize;//文件大小
    @TableField(exist = false)
    private String fileStatus;//文件状态

}
