package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.System_menu;
import com.xvls.alexander.service.System_menuService;
import com.xvls.alexander.utils.SystemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/system_menu")
public class System_menuController {

    @Autowired
    System_menuService system_menuService;

    /**
     * 通过 userId 获得菜单列表
     * @param userId
     * @return
     */
    @RequestMapping("getMenuListById")
    public Object getMenuListById(@RequestParam("userId") Integer userId){
        if(userId == null){
            return SystemResponse.badArgument();
        }
        List<System_menu> menuList = system_menuService.getMenuListById(userId);
        Map result = Maps.newHashMap();
        result.put("menuList",menuList);

        return SystemResponse.ok(result);
    }
}
