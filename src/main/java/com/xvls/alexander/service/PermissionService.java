package com.xvls.alexander.service;

import com.xvls.alexander.entity.Permission;

import java.util.List;

public interface PermissionService {

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
