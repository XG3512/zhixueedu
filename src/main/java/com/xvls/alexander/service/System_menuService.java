package com.xvls.alexander.service;

import com.xvls.alexander.entity.System_menu;

import java.util.List;

/**
 * 系统菜单
 */
public interface System_menuService {

    /**
     * 通过 userId 获得menu
     * @param userId
     * @return
     */
    List<System_menu> getMenuListById(Integer userId);

    /**
     * 获得所有菜单列表
     * @return
     */
    List<System_menu> getAllMenuList();

    /**
     * 添加菜单
     * @param system_menu
     * @return
     */
    Integer addMenu(System_menu system_menu);

    /**
     * 更新菜单
     * @param system_menu
     */
    void updateMenu(System_menu system_menu);

    /**
     * 通过 menuId 删除菜单
     * @param menuId
     */
    void deleteMenu(Integer menuId);
}
