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
        return system_menuMapper.getMenuListById(userId);
    }
}
