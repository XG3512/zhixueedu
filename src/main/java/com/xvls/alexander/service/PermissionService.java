package com.xvls.alexander.service;

import com.xvls.alexander.entity.Permission;

import java.util.List;

public interface PermissionService {

    /**
     * 获得所有的权限列表
     * @return
     */
    List<Permission> getAllPermissionList();
}
