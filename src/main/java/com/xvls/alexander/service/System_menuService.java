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
}
