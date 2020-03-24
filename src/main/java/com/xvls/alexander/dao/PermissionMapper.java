package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 获得所有的权限列表
     * @return
     */
    List<Permission> getAllPermissionList();

    /**
     * 通过 roleId 获得所有permission 并对已有权限进行标记
     * @param roleId
     * @return
     */
    List<Permission> getRolePermissions(Integer roleId);
}
