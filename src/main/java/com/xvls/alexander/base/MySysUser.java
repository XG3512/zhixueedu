package com.xvls.alexander.base;

import com.xvls.alexander.entity.wx.Users;
import org.apache.shiro.SecurityUtils;

/**
 * Created by wangl on 2017/11/25.
 * todo:
 */
public class MySysUser {
    /**
     * 取出Shiro中的当前用户LoginName.
     */
    public static String icon() {
        return ShiroUser().getIcon();
    }

    public static Integer id() {
        return ShiroUser().getUserId();
    }

    public static String loginNum() {
        return ShiroUser().getUserNum();
    }

    public static String nickName(){
        return ShiroUser().getUserName();
    }

    public static Users ShiroUser() {
        Users user = (Users) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
}
