package com.xvls.alexander;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xvls.alexander.dao.*;
import com.xvls.alexander.entity.*;
import com.xvls.alexander.entity.wx.*;
import com.xvls.alexander.service.impl.UserServiceImpl;
import com.xvls.alexander.service.wx.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlexanderApplication.class)
public class TestUserMapper2 {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UsersService usersService;
    @Autowired
    UsersMapper usersMapper;

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
        Users users = usersService.getWxStudentInfoByUserNum("2017414540");
        System.out.println(users);
    }

    /**
     * 测试保存 WxUserInfo
     */
    @Test
    public void saveWxStudentInfo(){
        Users users = new Users();
        users.setUserNum("1102837040");
        /*ArrayList<Role> arrayList = new ArrayList<Role>();
        arrayList.add(new Role(10,"测试"));
        wxUserInfo.setRoleList(arrayList);*/
        users.insert();
    }

    @Test
    public void getWxUserInfoByUser_num_MP(){
        Users users = new Users();

        QueryWrapper<Users> WxUserInfoQueryWrapper = new QueryWrapper<>();
        WxUserInfoQueryWrapper.eq("nickname", "Glitter");
        List<Users> userList = users.selectList(WxUserInfoQueryWrapper);

        for(Users users1 : userList){
            System.out.println(users1);
        }
    }

    @Test
    public void selectAllWxUserInfo(){
        Users users = new Users();
        users.setUserId(2);

        Users users2 = users.selectById();

        System.out.println(users2);
    }

    /**
     * 根据用户学号，进行MD5加密，并进行信息更新
     */
    @Test
    public void updateWxUserInfoByUser_num(){

        Users users = usersService.getWxStudentInfoByUserNum("2017416481");

        if(users==null){
            System.out.println(users);
            return;
        }
        String password = users.getPassword();

        String salt = UUID.randomUUID().toString();
        salt = salt.replaceAll("-","");
        users.setSalt(salt);
        System.out.println("salt:"+salt);

        //加密：MD5
        Md5Hash md5Hash = new Md5Hash(password, users.getSalt(),6);
        System.out.println("md5Hash.toHex() :"+md5Hash.toHex());
        users.setPassword(md5Hash.toHex());//注意一致

        //更新用户信息
        usersService.saveWxStudentInfo(users);
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
    @Autowired
    WxCommentsService wxCommentsService;
    @Test
    public void testWxCommentsMapper(){
        List<Comments> comments = wxCommentsService.getComments("A", 1);
        for(Comments comment : comments){
            System.out.println(comment);
        }
    }

    @Autowired
    WxArticleService wxArticleService;
    @Test
    public void testgetArticleById(){
        Article article = wxArticleService.getArticleById(2,1);
        System.out.println(article);
    }

    @Autowired
    WxVideoMapper wxVideoMapper;
    @Test
    public void testgetPublicArticleList(){
        /*List<Video_main> publicVideoList = wxVideoMapper.getPublicVideoList();
        for(Video_main video_main : publicVideoList){
            System.out.println(video_main);
        }*/
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(1);
        pageInfo.setPageSize(3);
        List<Article> articleByPage = wxArticleService.getArticleByPage(pageInfo,1);
        for(Article article : articleByPage){
            System.out.println(article);
            System.out.println();
        }
    }

    @Autowired
    WxSchoolService wxSchoolService;
    @Autowired
    WxNoticeService wxNoticeService;
    @Test
    public void testGetSchoolMainPage(){
        School schoolInfo = wxSchoolService.getSchoolInfo(1,1);
        System.out.println(schoolInfo);
        List<Article> articleList = wxArticleService.getArticleBySchoolId(1,1);
        for(Article article : articleList){
            System.out.println(article);
        }
        System.out.println("-------------------------------------");
        List<Notice> noticeList = wxNoticeService.getNoticeListBySchoolId(1);
        for(Notice notice : noticeList){
            System.out.println(notice);
        }

        List<Video_main> video_mainList = wxVideoMapper.getPublicVideoListBySchoolId(1,1);
        for(Video_main video_main :video_mainList){
            System.out.println(video_main);
        }
    }

    @Autowired
    WxVideoService videoService;
    @Test
    public void testGetVideoInfoById(){
        /*Video videoInfoById = videoService.getVideoInfoById(1, 1);
        System.out.println(videoInfoById);*/

        Video_main videoMainInfo = videoService.getVideoMainInfo(4,1);
        System.out.println(videoMainInfo);
        videoService.updateVideoHeatOfVideo(videoMainInfo);
    }

    @Autowired
    WxToolBarService wxToolBarService;
    @Test
    public void testGoodService(){
        Good good = new Good("N",1,2,new Date());
        wxToolBarService.addNoticeGoodNum(good);
//        wxToolBarService.decreaseNoticeGoodNum(good);
    }

    @Test
    public void testGetUserId(){
        Integer userId = usersMapper.getUserId("2017414540", 1, "学生");
        System.out.println(userId);
    }

    @Test
    public void testGetSchoolList(){
        List<SchoolList> schoolList = wxSchoolService.getSchoolList();
        System.out.println(schoolList);
    }

    @Test
    public void testReturnAutoId(){
        Users users = new Users();
        int insert = usersMapper.insert(users);
        System.out.println(insert);//1
        System.out.println(users.getUserId());//16
    }

    @Autowired
    WxUserService wxUserService;

    @Test
    public void testUpdateLoginInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname("Glitter");
        wxUserService.updateLoginInfo(1,userInfo);
    }

    @Autowired
    WxV_historyService wxV_historyService;
    @Test
    public void testAddV_history(){
        V_history v_history = new V_history();
        v_history.setVideoMainId(1);
        v_history.setWxUserId(1);
        v_history.setEpisodeId(1);
        v_history.setWatchDate(new Date());
        v_history.setWatchTo(0);
        wxV_historyService.addV_history(v_history);
    }

    @Autowired
    CollectionService collectionService;
    @Test
    public void testAddCollection(){
        WxCollection wxCollection = new WxCollection();
        wxCollection.setCollectionDate(new Date());
        wxCollection.setCollectionId(1);
        wxCollection.setCollectionType("A");
        wxCollection.setGroupId(1);
        wxCollection.setWxUserId(1);
        collectionService.addCollection(wxCollection);
    }

    @Autowired
    WxHomeRotationService wxHomeRotationService;
    @Test
    public void testWxHomeRotation(){
        List<WxHomeRotation> wxHomeRotationList = wxHomeRotationService.getAllHomeRotation();
        for(WxHomeRotation wxHomeRotation : wxHomeRotationList){
            System.out.println(wxHomeRotation);
        }
    }

}
