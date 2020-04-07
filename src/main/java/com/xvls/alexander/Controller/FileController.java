package com.xvls.alexander.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.xvls.alexander.annotation.SysLog;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.entity.PageInfo;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.service.FileCrudService;
import com.xvls.alexander.service.QiniuService;
import com.xvls.alexander.service.wx.WxHomeRotationService;
import com.xvls.alexander.service.wx.WxVideoRotationService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.SystemResponse;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.RestResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    QiniuFileUtil qiniuFileUtil;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    FileCrudService fileCrudService;

    @Autowired
    WxHomeRotationService wxHomeRotationService;

    @Autowired
    WxVideoRotationService wxVideoRotationService;

    /**
     * 普通上传图片,需要上传文件,userId,belongType,belongId,name,
     * @param file
     * @param httpServletRequest
     * @return
     */
    @PostMapping("uploadFile")
    public RestResponse uploadFile(@RequestParam("file")MultipartFile file,
                                   HttpServletRequest httpServletRequest){
        String getParameter = httpServletRequest.getParameter("file_belong");
        //System.out.println(getParameter);
        File_belong file_belong = new Gson().fromJson(getParameter,File_belong.class);
        System.out.println(file_belong);
        if(file_belong==null){
            return RestResponse.failure("加载信息错误，参数传入错误");
        }
        if(file==null){
            return RestResponse.failure("上传文件为空");
        }
        Map map = Maps.newHashMap();
        try {
            map=qiniuService.upload(file,file_belong);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.failure(e.getMessage());
        }
        return RestResponse.success().setData(map);
    }

    /**
     * 通过 file，homeRotationId 更改首页轮播图图片
     * @param file
     * @param homeRotationId
     * @return
     */
    @RequiresPermissions("home_rotation:update")
    @RequestMapping("uploadHomeRotationImage")
    public Object uploadHomeRotationImage(@RequestParam("file")MultipartFile file,@RequestParam("homeRotationId") Integer homeRotationId){
        if(file == null || homeRotationId == null){
            return SystemResponse.badArgument();
        }
        File_download file_download = null;
        try {
            file_download = qiniuService.uploadEditorFile(file);
            if (file_download==null){
                return SystemResponse.fail(-1,"上传失败");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"上传失败");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"上传失败");
        }
        wxHomeRotationService.updateHomeRotationSourse(homeRotationId,file_download.getFileUrl());
        Map result = Maps.newHashMap();
        result.put("url",file_download.getFileUrl());
        return SystemResponse.ok(result);
    }

    /**
     * 通过 file，videoRotationId 更新视频轮播图图片
     * @param file
     * @param videoRotationId
     * @return
     */
    @RequiresPermissions("video_rotation:update")
    @RequestMapping("updateVideoRotationImage")
    public Object updateVideoRotationImage(@RequestParam("file")MultipartFile file,@RequestParam("videoRotationId") Integer videoRotationId){
        if(file==null || videoRotationId == null){
            return SystemResponse.badArgument();
        }
        try {
            String url = wxVideoRotationService.updateVideoRotationImage(file, videoRotationId);
            Map result = Maps.newHashMap();
            result.put("url",url);
            return SystemResponse.ok(result);
        } catch (IOException e) {
            System.out.println(e);
            return SystemResponse.fail(-1,e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            return SystemResponse.fail(-1,e.getMessage());
        }
    }

    /**
     * 删除已经上传的图片
     * @param imgPath
     */
    @PostMapping("deleteQiniu")
    public Object deleteQiniu(String imgPath){
        if(imgPath.isEmpty()){
            return RestResponse.failure("输入参数为空");
        }
        return qiniuService.deleteQiniu(imgPath);
    }

    /**
     * 上传网络图片
     * @param src
     * @return
     */
    @PostMapping("uploadImageSrc")
    public String uploadImageSrc(String src){
        return null;
    }

    /**
     * 上传本地图片
     * @param src
     * @return
     */
    @PostMapping("uploadLocalImg")
    public String uploadLocalImg(String src){
        return null;
    }

    /**
     * 上传base64位的图片
     * @param base64
     * @param name
     * @return
     */
    @PostMapping("uploadBase64")
    public RestResponse uploadBase64(@RequestParam(value = "file",required = false) String base64,
                               @RequestParam(value = "name" ,required = false) String name){
        return qiniuFileUtil.uploadBase64(base64,name);
    }

    /**
     * 批量上传文件
     * @param files
     * @param httpServletRequest
     * @return
     */
    @PostMapping("uploadManyFile")
    public RestResponse uploadManyFile(@RequestParam("userId") int userId,
                                       //@RequestParam(value = "file_belong",required = false) File_belong file_belong,
                                       @RequestParam("files")MultipartFile[] files,
                                       HttpServletRequest httpServletRequest){
        File_belong file_belong = JacksonUtil.parseObject(httpServletRequest.getParameter("file_belong"),File_belong.class);
        if(files == null){
            return RestResponse.failure("上传文件不能为空");
        }
        if(userId==0){
            return RestResponse.failure("内容有误,请重新登录");
        }
        List<Object> lists = Lists.newArrayList();
        try{
            for(int i=0;i<files.length;i++){
                file_belong.setUserId(userId);
                lists.add(qiniuService.upload(files[i],file_belong));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.failure(e.toString());
        }
        return RestResponse.success().setData(lists);
    }

    /**
     * 富文本 文件上传，返回
     * @param file
     * @param httpServletRequest
     * @return
     */
    @RequiresPermissions("article:add")
    @RequestMapping("uploadEditorFile")
    public Object uploadEditorFile(@RequestParam("file") MultipartFile file,HttpServletRequest httpServletRequest){
        if(file == null){
            return SystemResponse.badArgument();
        }

        File_download file_download = null;

        try {
            file_download = qiniuService.uploadEditorFile(file);
            if (file_download==null){
                return SystemResponse.fail(-1,"上传失败");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"上传失败");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return SystemResponse.fail(-1,"上传失败");
        }

        Map result = Maps.newHashMap();
        result.put("fileHash",file_download.getFileHash());
        result.put("file_url",file_download.getFileUrl());
        result.put("name",file_download.getName());

        return SystemResponse.ok(result);
    }

    /**
     * 根据用户id，获取用户所上传的资料
     * @param httpServletRequest
     * @param userId
     * @return
     */
    @RequestMapping(value = "getFileList")
    public RestResponse getFileList(@RequestParam("userId") int userId,HttpServletRequest httpServletRequest){

        PageInfo pageInfo = JacksonUtil.parseObject(httpServletRequest.getParameter("pageInfo"), PageInfo.class);
        System.out.println(pageInfo);
        System.out.println(userId);
        if(userId==0||pageInfo==null){
            return RestResponse.failure("参数错误");
        }
        List<File_belong> fileList = fileCrudService.getFileList(userId, pageInfo);

        return RestResponse.success().setData(fileList);
    }

    /**
     * 批量删除资料信息，仅仅是删除file_belong中的信息，并未删除真正的资料
     * @param userId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("deleteFiles")
    public RestResponse deleteFiles(@RequestParam("userId") int userId , HttpServletRequest httpServletRequest){

        return null;
    }

    /**
     * 上传 学校（S）或教师（T）的背景图片
     * @param file
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("uploadBackgroundImg")
    @SysLog("上传 学校（S）或教师（T）的背景图片")
    public Object uploadBackgroundImg(@RequestParam("file")MultipartFile file,
                                            @RequestParam("type") String type,
                                            @RequestParam("id") Integer id){
        if(file==null||file.isEmpty()||type==null||id==null){
            return SystemResponse.badArgument();
        }
        Map map = Maps.newHashMap();
        try {
            map = qiniuService.uploadBackgroundImg(file,type,id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return SystemResponse.ok(map);
    }

    /**
     * 上传 学校（S）或教师（T）的头像图片
     * @param file
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("uploadIconImg")
    @SysLog("上传 学校（S）或教师（T）的头像图片")
    public Object uploadIconImg(@RequestParam("file") MultipartFile file,
                                      @RequestParam("type") String type,
                                      @RequestParam("id") Integer id){
        if(file==null||file.isEmpty()||type==null||id==null){
            return SystemResponse.badArgument();
        }
        Map map = Maps.newHashMap();
        try {
            map = qiniuService.uploadIconImg(file,type,id);
        } catch (IOException e) {
            System.out.println(e);
            return SystemResponse.fail(-1,e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            return SystemResponse.fail(-1,e.getMessage());
        }
        return SystemResponse.ok(map);
    }

    /**
     * 上传视频
     * @param file
     * @return
     */
    @RequiresPermissions("video_main:add")
    @RequestMapping("uploadVideo")
    @SysLog("上传视频")
    public Object uploadVideo(@RequestParam("file") MultipartFile file ,
                              @RequestParam("teacherId")Integer teacherId,
                              @RequestParam("videoTitle") String videoTitle,
                              @RequestParam("videoMainId") Integer videoMainId,
                              @RequestParam("episodeId") Integer episodeId,
                              @RequestParam("publicStatus") String publicStatus,
                              HttpServletRequest httpServletRequest){
        /*try {
            video = JacksonUtil.parseObject(httpServletRequest.getParameter("video"),Video.class);
        } catch (Exception e) {
            return RestResponse.failure("参数错误");
        }*/
        if(file==null || teacherId == null || videoTitle == null || videoMainId == null || episodeId == null || publicStatus == null){
            return SystemResponse.badArgument();
        }
        Video video = new Video();
        video.setTeacherId(teacherId);
        video.setVideoTitle(videoTitle);
        video.setVideoMainId(videoMainId);
        video.setEpisodeId(episodeId);
        video.setPublicStatus(publicStatus);
        try {
            return qiniuService.uploadVideo(file,video);
        } catch (IOException e) {
            return SystemResponse.fail(-1,e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return SystemResponse.fail(-1,e.getMessage());
        }
    }
}
