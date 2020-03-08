package com.xvls.alexander.Controller;

import com.google.common.collect.Maps;
import com.xvls.alexander.entity.System_menu;
import com.xvls.alexander.service.System_menuService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 获得所有菜单列表
     * @return
     */
    @RequiresPermissions("system_menu:selectAll")
    @RequestMapping("getAllMenuList")
    public Object getAllMenuList(){
        List<System_menu> menuList = system_menuService.getAllMenuList();
        Map result = Maps.newHashMap();
        result.put("menuList",menuList);
        return SystemResponse.ok(result);
    }

    /**
     * 增加 menu
     * @param body
     * @return
     */
    @RequiresPermissions("system_menu:add")
    @RequestMapping("addMenu")
    public Object addMenu(@RequestBody String body){
        System_menu system_menu = null;
        try {
            system_menu = JacksonUtil.parseObject(body,System_menu.class);
            if(system_menu == null){
                return SystemResponse.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"Json转换错误");
        }
        Integer menuId = system_menuService.addMenu(system_menu);
        Map result = Maps.newHashMap();
        result.put("menuId",menuId);
        return SystemResponse.ok(result);
    }

    /**
     * 通过 system_menu 更新菜单
     * @param body
     * @return
     */
    @RequiresPermissions("system_menu:update")
    @RequestMapping("updateMenu")
    public Object updateMenu(@RequestBody String body){
        System_menu system_menu = null;
        try {
            system_menu = JacksonUtil.parseObject(body,System_menu.class);
            if(system_menu == null || system_menu.getMenuId() == null){
                return SystemResponse.badArgument();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"Json转换错误");
        }
        system_menuService.updateMenu(system_menu);
        return SystemResponse.ok();
    }

    /**
     * 通过 menuId 删除菜单及其子菜单
     * @param menuId
     * @return
     */
    @RequiresPermissions("system_menu:delete")
    @RequestMapping("deleteMenu")
    public Object deleteMenu(@RequestParam("menuId") Integer menuId){
         if(menuId == null){
             return SystemResponse.badArgument();
         }
         system_menuService.deleteMenu(menuId);
         return SystemResponse.ok();
    }
}
