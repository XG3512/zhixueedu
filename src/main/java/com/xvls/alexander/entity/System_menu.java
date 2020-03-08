package com.xvls.alexander.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 系统菜单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_menu")
public class System_menu extends Model<System_menu> {

    @TableId(type = IdType.AUTO)
    private Integer menuId;
    private String path;
    private String menuName;
    private String menuLabel;
    private String menuUrl;
    private Integer parentId;
    private String icon;
    private Date createTime;
    private Integer roleId;
    private Integer orderNum;

    @TableField(exist = false)
    List<System_menu> members;
}
