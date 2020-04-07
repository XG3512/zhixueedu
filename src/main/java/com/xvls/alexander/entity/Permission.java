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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("permission")
public class Permission extends Model<Permission> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer permissionId;
    private String permissionName;
    private String permissionCode;
    private Integer parentId;

    @TableField(exist = false)
    private Integer roleId;

    @TableField(exist = false)
    private Integer roleIdFlag;

    @TableField(exist = false)
    private List<Permission> children;

}
