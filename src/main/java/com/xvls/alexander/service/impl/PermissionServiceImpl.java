package com.xvls.alexander.service.impl;

import com.xvls.alexander.dao.PermissionMapper;
import com.xvls.alexander.entity.Permission;
import com.xvls.alexander.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 获得所有的权限列表
     * @return
     */
    @Override
    public List<Permission> getAllPermissionList() {
        return permissionMapper.getAllPermissionList();
    }

    /**
     * 通过 roleId 获得所有permission 并对已有权限进行标记
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> getRolePermissions(Integer roleId) {
        return permissionMapper.getRolePermissions(roleId);
    }
}
