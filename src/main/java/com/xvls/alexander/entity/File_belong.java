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

    private int userId;
    private char belongType;// V(视频) | A(文章)
    private int belongId;
    private String fileHash;//文件标识
    private String name;//原名称

}
