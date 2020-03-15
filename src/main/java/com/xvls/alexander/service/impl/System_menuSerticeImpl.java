package com.xvls.alexander.service.impl;

import com.xvls.alexander.dao.System_menuMapper;
import com.xvls.alexander.entity.System_menu;
import com.xvls.alexander.service.System_menuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统菜单
 */
@Service
public class System_menuSerticeImpl implements System_menuService {

    @Autowired
    System_menuMapper system_menuMapper;

    /**
     * 通过 userId 获得menu
     * @param userId
     * @return
     */
    @Override
    public List<System_menu> getMenuListById(Integer userId) {
        List<System_menu> menuList = system_menuMapper.getMenuListById(userId);
        //System.out.println("menuList.size():"+menuList.size());
        if(menuList.size()==1){
            return menuList.get(0).getChildren();
        }else{
            return menuList;
        }
    }

    /**
     * 获得所有菜单列表
     * @return
     */
    @Override
    public List<System_menu> getAllMenuList() {
        return system_menuMapper.getAllMenuList();
    }

    /**
     * 添加菜单
     * @param system_menu
     * @return
     */
    @Override
    public Integer addMenu(System_menu system_menu) {
        int insert = system_menuMapper.insert(system_menu);
        return system_menu.getMenuId();
    }

    @Override
    public void updateMenu(System_menu system_menu) {
        system_menuMapper.updateById(system_menu);
    }

    @Override
    public void deleteMenu(Integer menuId) {
        system_menuMapper.deleteMenu(menuId);
    }
}
