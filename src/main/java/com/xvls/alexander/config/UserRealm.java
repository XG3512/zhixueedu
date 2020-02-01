package com.xvls.alexander.config;

import com.xvls.alexander.entity.User;
import com.xvls.alexander.entity.wx.WxUserInfo;
import com.xvls.alexander.service.UserService;
import com.xvls.alexander.service.impl.UserServiceImpl;
import com.xvls.alexander.service.wx.WxUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

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
        //从数据库中添加权限
        info.addStringPermission("user:add");

        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();//拿到user对象
        //info.addStringPermission(currentUser.getPerms()); //授权

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

        //可以加密： md5+盐值加密
        //密码认证，shiro自己来做，加密
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
