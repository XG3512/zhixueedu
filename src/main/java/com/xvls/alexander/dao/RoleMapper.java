package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {

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
     * 给 role 添加 permissions
     * @param roleId
     * @param permissions
     */
    void addRole_permissions(Integer roleId, List<Integer> permissions);

    /**
     * 对用户 增加角色、修改角色
     * @param userId
     * @param roleIdList
     */
    void updateUserRole(Integer userId,List<Integer> roleIdList);

    /**
     * 批量删除 role
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
     * 通过 userIdList 删除user_role中的内容
     * @param userIdList
     */
    void deleteUser_roleByUserId(List<Integer> userIdList);

    /**
     * 通过 roleId,roleName,editTime 修改 角色名称 和 编辑时间
     * @param roleId
     * @param roleName
     * @param editTime
     */
    void updateRoleName_EditTimeById(Integer roleId, String roleName, Date editTime);

    /**
     * 通过 roleId 删除role_permission中对应的数据
     * @param roleId
     */
    void deleteRole_permissionByRoleId(Integer roleId);
    /**
     * 通过 roleId,permissionIdList 修改 角色的权限
     * @param roleId
     * @param permissionIdList
     */
    void insertRole_permission(Integer roleId,List<Integer> permissionIdList);
}
