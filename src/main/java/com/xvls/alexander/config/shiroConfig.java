package com.xvls.alexander.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    //3 shiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);


        //添加shiro的内置过滤器
        /*
        * anon：无需认证就可以访问
        * authc：必须认证了才能访问
        * user：必须拥有记住我功能才能用
        * perms：拥有对某个资源的权限才能访问
        * role：拥有某个角色权限才能访问
        * */
        //拦截
        Map<String, String> filterMap = new LinkedHashMap<String,String>();

        //授权
        filterMap.put("/user/add","perms[user:add]");

        filterMap.put("/user/add","anon");

        filterMap.put("/user/update","authc");

        //设置登录的请求
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //设置未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    //2 DafaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getdefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //1 创建 realm对象 ， 需要自定义
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
