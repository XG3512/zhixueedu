package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.Permission;
import com.xvls.alexander.service.PermissionService;
import com.xvls.alexander.utils.SystemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    /**
     * 获得权限树
     * @return
     */
    @RequestMapping("getAllPermissionList")
    public Object getAllPermissionList(){
        List<Permission> permissionList = permissionService.getAllPermissionList();
        Map result = Maps.newHashMap();
        result.put("permissionList",permissionList);
        return SystemResponse.ok(result);
    }

}
