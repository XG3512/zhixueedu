package com.xvls.alexander.service.impl;

import com.google.common.collect.Lists;
import com.xvls.alexander.dao.RoleMapper;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.Role;
import com.xvls.alexander.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    /**
     * 通过 pageInfo 获得 roleList
     * @param pageInfo
     * @return
     */
    @Override
    public List<Role> getRoleList(PageInfo pageInfo) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return roleMapper.getRoleList(new PageInfo(pageNum,pageSize));
    }

    /**
     * 通过 pageInfo 和 roleName 进行模糊查询
     * @param pageInfo
     * @param roleName
     * @return
     */
    @Override
    public List<Role> getRoleListByName(PageInfo pageInfo, String roleName) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        roleName = "%"+roleName+"%";
        return roleMapper.getRoleListByName(new PageInfo(pageNum,pageSize),roleName);
    }

    /**
     * 获得所有角色
     * @return
     */
    @Override
    public List<Role> getAllRole() {
        return roleMapper.selectList(null);
    }

    /**
     * 获取角色数据总条数
     * @return
     */
    @Override
    public Integer getRoleCount() {
        return roleMapper.selectCount(null);
    }

    /**
     * 增加角色
     * @param role
     */
    @Override
    public Integer addRole(Role role) {
        int insert = roleMapper.insert(role);
        return role.getRoleId();
    }

    /**
     * 给 role 添加 permissions
     * @param roleId
     * @param permissions
     */
    @Override
    public void addRole_permissions(Integer roleId, List<Integer> permissions) {
        roleMapper.addRole_permissions(roleId,permissions);
    }

    /**
     * 对用户 增加角色、修改角色
     * @param userId
     * @param roleIdList
     */
    @Override
    public void updateUserRole(Integer userId, List<Integer> roleIdList) {
        List<Integer> userIdList = Lists.newArrayList();
        userIdList.add(userId);
        this.deleteUser_roleByUserId(userIdList);
        roleMapper.updateUserRole(userId,roleIdList);
    }


    /**
     * 批量删除 role 中相应的内容
     * @param roleIdList
     */
    @Override
    public void deleteRole(List<Integer> roleIdList) {
        roleMapper.deleteRole(roleIdList);
    }
    /**
     * 通过 roleIdList 删除role_permission中的相应内容
     * @param roleIdList
     */
    @Override
    public void deleteRole_permissionByRoleIdList(List<Integer> roleIdList) {
        roleMapper.deleteRole_permissionByRoleIdList(roleIdList);
    }
    /**
     * 通过 roleIdList 删除user_role中的相应内容
     * @param roleIdList
     */
    @Override
    public void deleteUser_roleByRoleIdList(List<Integer> roleIdList) {
        roleMapper.deleteUser_roleByRoleIdList(roleIdList);
    }

    /**
     * 通过 userIdList 删除user_role中的内容
     * @param userIdList
     */
    @Override
    public void deleteUser_roleByUserId(List<Integer> userIdList) {
        roleMapper.deleteUser_roleByUserId(userIdList);
    }


    /**
     * 通过 roleId,roleName,editTime 修改 角色名称 和 编辑时间
     * @param roleId
     * @param roleName
     * @param editTime
     */
    @Override
    public void updateRoleName_EditTimeById(Integer roleId, String roleName, Date editTime) {
        roleMapper.updateRoleName_EditTimeById(roleId,roleName,editTime);
    }

    /**
     * 通过 roleId,permissionIdList 修改 角色的权限
     * @param roleId
     * @param permissionIdList
     */
    @Override
    public void updateRole_permission(Integer roleId, List<Integer> permissionIdList) {
        /*先删除对应的权限*/
        roleMapper.deleteRole_permissionByRoleId(roleId);
        /*再添加权限*/
        roleMapper.insertRole_permission(roleId,permissionIdList);
    }


}
