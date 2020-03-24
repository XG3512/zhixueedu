package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.Permission;
import com.xvls.alexander.entity.Role;
import com.xvls.alexander.service.PermissionService;
import com.xvls.alexander.service.RoleService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    /**
     * 通过 pageInfo 获得roleList
     * @param body
     * @return
     */
    @RequiresPermissions("role:select")
    @RequestMapping("getRoleList")
    public Object getRoleList(@RequestBody String body){
        PageInfo pageInfo = null;
        try {
            pageInfo = JacksonUtil.parseObject(body,PageInfo.class);
            if(pageInfo == null || pageInfo.getPageNum()==null || pageInfo.getPageSize()==null){
                return SystemResponse.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<Role> roleList = roleService.getRoleList(pageInfo);
        Map result = Maps.newHashMap();
        result.put("roleList",roleList);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 pageInfo 和 roleName 进行模糊查询
     * @param body
     * @return
     */
    @RequiresPermissions("role:select")
    @RequestMapping("getRoleListByName")
    public Object getRoleListByName(@RequestBody String body){
        PageInfo pageInfo = null;
        String roleName = null;
        try {
            pageInfo = JacksonUtil.parseObject(body,"pageInfo",PageInfo.class);
            roleName = JacksonUtil.parseString(body,"roleName");
            if(pageInfo == null || pageInfo.getPageNum() == null || pageInfo.getPageSize() == null || roleName == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<Role> roleList = roleService.getRoleListByName(pageInfo, roleName);
        Map result = Maps.newHashMap();
        result.put("roleList",roleList);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 roleId 获得所有permission 并对已有权限进行标记
     * @param body
     * @return
     */
    @RequiresPermissions("role:select")
    @RequestMapping("getRolePermissions")
    public Object getRolePermissions(@RequestBody String body){
        Integer roleId = null;
        try {
            roleId = JacksonUtil.parseInteger(body,"roleId");
            if(roleId == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        List<Permission> permissions = permissionService.getRolePermissions(roleId);
        Map result = Maps.newHashMap();
        result.put("permissions",permissions);
        return SystemResponse.ok(result);
    }

    /**
     * 获取角色数据总条数
     * @return
     */
    @RequiresPermissions("role:select")
    @RequestMapping("getRoleCount")
    public Object getRoleCount(){
        Integer roleCount = roleService.getRoleCount();
        Map result = Maps.newHashMap();
        result.put("roleCount",roleCount);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 role,permissions数组 增加角色
     * @param body
     * @return
     */
    @RequiresPermissions("role:add")
    @RequestMapping("addRole")
    public Object addRole(@RequestBody String body){
        Role role = null;
        List<Integer> permissions = null;
        try {
            role = JacksonUtil.parseObject(body,"role",Role.class);
            permissions = JacksonUtil.parseIntegerList(body,"permissions");
            if(role==null || role.getRoleName()==null || permissions == null){
                return SystemResponse.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        if(role.getCreateTime() == null){
            role.setCreateTime(new Date());
        }
        if(role.getEditTime() == null){
            role.setEditTime(new Date());
        }
        Integer roleId = roleService.addRole(role);
        roleService.addRole_permissions(roleId,permissions);
        Map result = Maps.newHashMap();
        result.put("roleId",roleId);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 roleId，permissionIdList 更新角色权限。会先把角色的所有权限删除，然后再把权限添加进去
     * @param body
     * @return
     */
    @RequiresPermissions("role:update")
    @RequestMapping("updateRolePermission")
    public Object updateRolePermission(@RequestBody String body){
        Integer roleId = null;
        List<Integer> permissionIdList = null;
        try {
            roleId = JacksonUtil.parseInteger(body,"roleId");
            permissionIdList = JacksonUtil.parseIntegerList(body,"permissionIdList");
            if(roleId == null || permissionIdList == null || permissionIdList.size() == 0){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        roleService.updateRole_permission(roleId,permissionIdList);
        return SystemResponse.ok();
    }

    /**
     * 通过 roleId，roleName 更新角色名称
     * @param body
     * @return
     */
    @RequiresPermissions("role:update")
    @RequestMapping("updateRoleName")
    public Object updateRoleName(@RequestBody String body){
        Integer roleId = null;
        String roleName = null;
        Date editTime = new Date();
        try {
            roleId = JacksonUtil.parseInteger(body,"roleId");
            roleName = JacksonUtil.parseString(body,"roleName");
            if(roleId == null || roleName == null){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        roleService.updateRoleName_EditTimeById(roleId,roleName,editTime);
        return SystemResponse.ok();
    }



    /**
     * 通过 roleIdList 批量删除 role 和 role_permission 和 user_role 中相应的内容
     * @param body
     * @return
     */
    @RequiresPermissions("role:delete")
    @RequestMapping("deleteRoles")
    public Object deleteRoles(@RequestBody String body){
        List<Integer> roleIdList = null;
        try {
            roleIdList = JacksonUtil.parseIntegerList(body,"roleIdList");
            if(roleIdList == null || roleIdList.size() == 0){
                return SystemResponse.badArgumentValue();
            }
        } catch (Exception e) {
            System.out.println(e);
            return SystemResponse.badArgument();
        }
        roleService.deleteUser_roleByRoleIdList(roleIdList);
        roleService.deleteRole_permissionByRoleIdList(roleIdList);
        roleService.deleteRole(roleIdList);
        return SystemResponse.ok();
    }
}
