package com.xvls.alexander;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.WxUserMapper;
import com.xvls.alexander.entity.User;
import com.xvls.alexander.entity.wx.WxUserInfo;
import com.xvls.alexander.service.impl.UserServiceImpl;
import com.xvls.alexander.service.wx.WxUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlexanderApplication.class)
public class TestUserMapper2 {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    WxUserService wxUserService;
    @Autowired
    WxUserMapper wxUserMapper;

    @Test
    public void testSelectById(){
        User user = new User();
        user.setId(2L);

        User user2 = user.selectById();
        System.out.println(user2);
    }

    @Test
    public void queryUserByName(){
        User user = userService.queryUserByName("GlitterR");
        System.out.println(user);
    }


    @Test
    public void getWxStudentInfoByUserNum(){
        WxUserInfo wxUserInfo = wxUserService.getWxStudentInfoByUserNum("2017414540");
        System.out.println(wxUserInfo);
    }

    /**
     * 测试保存 WxUserInfo
     */
    @Test
    public void saveWxStudentInfo(){
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setNickname("Glitter");
        wxUserInfo.setUserNum("1102837040");
        /*ArrayList<Role> arrayList = new ArrayList<Role>();
        arrayList.add(new Role(10,"测试"));
        wxUserInfo.setRoleList(arrayList);*/
        wxUserInfo.insert();
    }

    @Test
    public void getWxUserInfoByUser_num_MP(){
        WxUserInfo wxUserInfo = new WxUserInfo();

        QueryWrapper<WxUserInfo> WxUserInfoQueryWrapper = new QueryWrapper<>();
        WxUserInfoQueryWrapper.eq("nickname", "Glitter");
        List<WxUserInfo> wxUserInfos = wxUserInfo.selectList(WxUserInfoQueryWrapper);

        for(WxUserInfo wxUserInfo1:wxUserInfos){
            System.out.println(wxUserInfo1);
        }
    }

    @Test
    public void selectAllWxUserInfo(){
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setUserId(2);

        WxUserInfo wxUserInfo2 = wxUserInfo.selectById();

        System.out.println(wxUserInfo2);
    }

    @Test
    public void getWxUserInfoByOpenid(){
        WxUserInfo wxStudentInfoByOpenId = wxUserMapper.getWxStudentInfoByOpenId("123456");
        System.out.println(wxStudentInfoByOpenId);
    }

    /**
     * 根据用户学号，进行MD5加密，并进行信息更新
     */
    @Test
    public void updateWxUserInfoByUser_num(){

        WxUserInfo wxUserInfo = wxUserService.getWxStudentInfoByUserNum("2017414540");

        String password = wxUserInfo.getPassword();

        String salt = UUID.randomUUID().toString();
        salt = salt.replaceAll("-","");
        wxUserInfo.setSalt(salt);
        System.out.println("salt:"+salt);

        //加密：MD5
        Md5Hash md5Hash = new Md5Hash(password,wxUserInfo.getSalt(),6);
        System.out.println("md5Hash.toHex() :"+md5Hash.toHex());
        wxUserInfo.setPassword(md5Hash.toHex());//注意一致

        //更新用户信息
        wxUserService.saveWxStudentInfo(wxUserInfo);
    }

    @Test
    public void testUUID(){
        String s = UUID.randomUUID().toString();
        System.out.println(s.length());
        System.out.println(s);
        String s1 = s.replaceAll("-", "");
        System.out.println(s1.length());
        System.out.println(s1);
        System.out.println("---------");
        Md5Hash md5Hash = new Md5Hash("password","wxUserInfo",6);
        System.out.println(md5Hash.toHex());//与toString()方法的结果一样
        System.out.println(md5Hash.toBase64());
        System.out.println(md5Hash.toString());
    }


}
