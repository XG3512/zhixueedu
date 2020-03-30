package com.xvls.alexander.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    /*加密方式*/
    @Value("${xvls_shiro.setHashAlgorithmName}")
    private String setHashAlgorithmName;

    /*迭代次数*/
    @Value("${xvls_shiro.setHashIterations}")
    private int setHashIterations;

    /*此处的设置，true加密用的hex编码，false用的base64编码*/
    @Value("${xvls_shiro.setStoredCredentialsHexEncoded}")
    private boolean setStoredCredentialsHexEncoded;


    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;

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

        Map<String, Filter> filter = new HashMap<>();
        filter.put("custom",new ShiroUserFilter());
        shiroFilterFactoryBean.setFilters(filter);

        //拦截
        Map<String, String> filterMap = new HashMap<>();
        //授权

        filterMap.put("/user/add","perms[user:add]");

        filterMap.put("/user/add","anon");

        filterMap.put("/user/update","custom");

        /**微信端*/
        filterMap.put("/system/users/getWxUsersInfoByIdd","anon");
        filterMap.put("/system/users/wxUpdatePassword","custom");
        filterMap.put("/system/users/wxUpdatePersonalInfo","custom");
        filterMap.put("/system/users/wxUpdatePhone","custom");
        filterMap.put("/system/users/wxUpdateMail","custom");
        filterMap.put("/system/users/wxUpdateMotto","custom");

        /**后台管理端url管理*/
        filterMap.put("/SignIn/SignIn","anon");
        filterMap.put("/system/users/getWxUsersInfoById","anon");

        /*动态*/
        filterMap.put("/system/article/**","custom");
        /*系统菜单*/
        filterMap.put("/system/system_menu/**","custom");
        /*签到*/
        filterMap.put("/SignIn/**","custom");
        /*filterMap.put("/SignIn/ReGetSignInImage","custom");
        filterMap.put("/SignIn/getSignInInfoListByUserId","custom");
        filterMap.put("/SignIn/getSignInInfoCount","custom");
        filterMap.put("/SignIn/deleteSignInInfo","custom");
        filterMap.put("/SignIn/getSignIn_userList","custom");
        filterMap.put("/SignIn/getSignIn_userCount","custom");*/

        filterMap.put("/system/tc/**","custom");
        /*角色*/
        filterMap.put("/system/role/**","custom");
        /*通知*/
        filterMap.put("/system/notice/**","custom");
        /*教师视频管理*/
        filterMap.put("/system/video/**","custom");
        /*主页轮播图管理*/
        filterMap.put("/system/homeRotation","custom");
        /*视频轮播图管理*/
        filterMap.put("/system/videoRotation","custom");
        /*用户管理*/
        filterMap.put("/system/users/**","custom");

        //设置登录的请求
        //shiroFilterFactoryBean.setLoginUrl("/toLogin");
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
        /**配置redis缓存*/
        securityManager.setCacheManager(cacheManager());
        /** 配置session */
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //1 创建 realm对象 ， 需要自定义
    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式
        credentialsMatcher.setHashAlgorithmName(setHashAlgorithmName);
        //加密次数
        credentialsMatcher.setHashIterations(setHashIterations);
        //此处的设置，true加密用的hex编码，false用的base64编码
        credentialsMatcher.setStoredCredentialsHexEncoded(setStoredCredentialsHexEncoded);
        return credentialsMatcher;
    }

    /**
     * 自定义sessionManager，用户的唯一标识，即Token或Authorization的认证
     */
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        /**sessionDao*/
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }
    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        // redisManager.setExpire(18000);// 配置缓存过期时间
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    /*@Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }*/
    @Bean
        public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
