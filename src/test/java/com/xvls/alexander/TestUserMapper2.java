package com.xvls.alexander;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.*;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.entity.School;
import com.xvls.alexander.entity.User;
import com.xvls.alexander.entity.wx.*;
import com.xvls.alexander.service.impl.UserServiceImpl;
import com.xvls.alexander.service.wx.*;
import com.xvls.alexander.utils.CalculateUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.awt.image.BufferedImage;
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

    @Autowired
    FileUploadMapper fileUploadMapper;
    @Test
    public void testFile_download(){
        /*File_download file_download = fileUploadMapper.selectOneByHash("2");
        System.out.println(file_download);*/
        QueryWrapper<File_download> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_hash","1");
        List<File_download> file_downloads = fileUploadMapper.selectList(queryWrapper);
        for(File_download file_download1 : file_downloads){
            System.out.println(file_download1);
        }
    }

    @Test
    public void testString(){
         String path="http://xvls.top/";
        String imgPath = "http://xvls.top/jpgc2477d97-b18d-4b7f-badc-fcdbd99f6d3a";
        imgPath = imgPath.replace(path, "");
        System.out.println(imgPath);
    }

    @Autowired
    FileBelongMapper fileBelongMapper;
    @Test
    public void testFile_belong(){
        File_belong file_belong = new File_belong();
        /*QueryWrapper<File_belong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",file_belong.getUserId())
                .eq("belong_type",file_belong.getBelongType())
                .eq("belong_id",file_belong.getBelongId());
        List<File_belong> file_belongs = file_belong.selectList(queryWrapper);*/
        List<File_belong> file_belongs = file_belong.selectAll();
        for(File_belong file_belong1 : file_belongs){
            System.out.println(file_belong1);
        }
    }

    @Test
    public void testQrCodeUtil(){
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        BufferedImage generate = QrCodeUtil.generate("http://hutool.cn/", config);

    }

    @Autowired
    FileCrudMapper fileCrudMapper;
    @Test
    public void testGetFileList(){
        List<File_belong> fileList = fileCrudMapper.getFileList(2, 1, 2);
        for(File_belong file_belong : fileList){
            System.out.println(file_belong);
        }
    }

    @Autowired
    WxCommentsMapper wxCommentsMapper;
    @Test
    public void testWxCommentsMapper(){
        List<Comments> comments = wxCommentsMapper.getAllComments("A", 1);
        for(Comments comment : comments){
            System.out.println(comment);
        }
    }

    @Autowired
    WxArticleService wxArticleService;
    @Test
    public void testgetArticleById(){
        Article article = wxArticleService.getArticleById(1);
        System.out.println(article);
    }

    @Autowired
    WxVideoMapper wxVideoMapper;
    @Test
    public void testgetPublicArticleList(){
        List<Video_main> publicVideoList = wxVideoMapper.getPublicVideoList();
        for(Video_main video_main : publicVideoList){
            System.out.println(video_main);
        }
    }

    @Autowired
    WxSchoolService wxSchoolService;
    @Autowired
    WxNoticeService wxNoticeService;
    @Test
    public void testGetSchoolMainPage(){
        School schoolInfo = wxSchoolService.getSchoolInfo(1);
        System.out.println(schoolInfo);
        List<Article> articleList = wxArticleService.getArticleBySchoolId(1);
        for(Article article : articleList){
            System.out.println(article);
        }
        System.out.println("-------------------------------------");
        List<Notice> noticeList = wxNoticeService.getNoticeListBySchoolId(1);
        for(Notice notice : noticeList){
            System.out.println(notice);
        }

        List<Video_main> video_mainList = wxVideoMapper.getPublicVideoListBySchoolId(1);
        for(Video_main video_main :video_mainList){
            System.out.println(video_main);
        }
    }

    @Autowired
    WxVideoService videoService;
    @Test
    public void testGetVideoInfoById(){
        Video videoInfoById = videoService.getVideoInfoById(1, 1);
        System.out.println(videoInfoById);

        Video_main videoMainInfo = videoService.getVideoMainInfo(1);
        System.out.println(videoMainInfo);
    }

}
