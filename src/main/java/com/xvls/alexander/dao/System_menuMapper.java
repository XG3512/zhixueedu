package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.System_menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface System_menuMapper extends BaseMapper<System_menu> {

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
     * 通过 menuId 删除菜单
     * @param menuId
     */
    void deleteMenu(Integer menuId);
}
