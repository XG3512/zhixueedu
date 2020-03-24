package com.xvls.alexander.service;

import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.Role;

import java.util.Date;
import java.util.List;

public interface RoleService {

    /**
     * 通过 pageInfo 获得 roleList
     * @param pageInfo
     * @return
     */
    List<Role> getRoleList(PageInfo pageInfo);

    /**
     * 通过 pageInfo 和 roleName 进行模糊查询
     * @param pageInfo
     * @param roleName
     * @return
     */
    List<Role> getRoleListByName(PageInfo pageInfo,String roleName);

    /**
     * 获取角色数据总条数
     * @return
     */
    Integer getRoleCount();

    /**
     * 增加角色
     * @param role
     */
    Integer addRole(Role role);

    /**
     * 给 role 添加 permissions
     * @param roleId
     * @param permissions
     */
    void addRole_permissions(Integer roleId,List<Integer> permissions);

    /**
     * 批量删除 role 和 role_permission 和 user_role 相应的内容
     * @param roleIdList
     */
    void deleteRole(List<Integer> roleIdList);
    /**
     * 通过 roleIdList 删除role_permission中的相应内容
     * @param roleIdList
     */
    void deleteRole_permissionByRoleIdList(List<Integer> roleIdList);
    /**
     * 通过 roleIdList 删除user_role中的相应内容
     * @param roleIdList
     */
    void deleteUser_roleByRoleIdList(List<Integer> roleIdList);

    /**
     * 通过 roleId,roleName,editTime 修改 角色名称 和 编辑时间
     * @param roleId
     * @param roleName
     * @param editTime
     */
    void updateRoleName_EditTimeById(Integer roleId, String roleName, Date editTime);

    /**
     * 通过 roleId,permissionIdList 修改 角色的权限
     * @param roleId
     * @param permissionIdList
     */
    void updateRole_permission(Integer roleId,List<Integer> permissionIdList);
}
