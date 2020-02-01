package com.xvls.alexander.config;

import com.google.common.collect.Sets;
import com.xvls.alexander.entity.Permission;
import com.xvls.alexander.entity.Role;
import com.xvls.alexander.entity.User;
import com.xvls.alexander.entity.wx.WxUserInfo;
import com.xvls.alexander.service.UserService;
import com.xvls.alexander.service.impl.UserServiceImpl;
import com.xvls.alexander.service.wx.WxUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    WxUserService wxUserService;

    /*授权*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        WxUserInfo currentUser = (WxUserInfo) subject.getPrincipal();//拿到user对象

        /** 从数据库中添加权限 **/
        Set<Role> roles = currentUser.getRoleList();
        Set<String> roleNames = Sets.newHashSet();
        for(Role role : roles){
            if(StringUtils.isNotBlank(role.getRoleName())){
                roleNames.add(role.getRoleName());
            }
        }
        info.setRoles(roleNames);

        info.addStringPermission("user:add");

        //info.addStringPermission(currentUser.getPerms());
        // 授权
        Set<Permission> permissions = currentUser.getPermissions();
        Set<String> permissionCodes = Sets.newHashSet();
        for(Permission permission : permissions){
            if(StringUtils.isNotBlank(permission.getPermissionCode())){
                permissionCodes.add(permission.getPermissionCode());
            }
        }
        info.setStringPermissions(permissionCodes);

        return info;
    }

    /*认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");

        //用户名，密码，数据库中取
        /*String name = "root";
        String password = "123456";*/

        UsernamePasswordToken userToken = (UsernamePasswordToken) token ;
        /*******连接真实数据库**********/
        WxUserInfo user = wxUserService.getWxStudentInfoByUserNum(userToken.getUsername());

        if(user==null){//没有这个人
            return null;//UnknownAccountException
        }

        /*if(!userToken.getUsername().equals(name)){
            return null;//抛出异常 UnknownAccountException
        }*/

        /**
         * info对象表示realm登录比对信息
         * 参数1：用户信息（真实登录中是登录对象，如user对象）
         * 参数2：密码
         * 参数3：盐
         * 参数4：当前类名
         */
        return new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),getName());
    }
}
