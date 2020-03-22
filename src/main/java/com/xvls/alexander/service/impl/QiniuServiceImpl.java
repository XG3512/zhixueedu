package com.xvls.alexander.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.service.QiniuService;
import com.xvls.alexander.service.wx.WxUsersService;
import com.xvls.alexander.service.wx.WxSchoolService;
import com.xvls.alexander.service.wx.WxVideoService;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

@Service
public class QiniuServiceImpl implements QiniuService {

    @Autowired
    QiniuFileUtil qiniuFileUtil;
    @Autowired
    WxSchoolService wxSchoolService;
    @Autowired
    WxUsersService wxUsersService;
    @Autowired
    WxVideoService wxVideoService;

    /**
     * 普通上传图片
     * @param file
     * @param file_belong
     * @return
     */
    @Override
    public Map<Object, Object> upload(MultipartFile file, File_belong file_belong) throws IOException, NoSuchAlgorithmException {
        Map map = Maps.newHashMap();
        File_download file_download = null;
        file_download = qiniuFileUtil.upload(file);
        map.put("url",file_download.getFileUrl());
        if(file_belong.getName()==null){
            file_belong.setName(file_download.getName());
        }
        file_belong.setFileHash(file_download.getFileHash());
        /**查询是否有重复的**/
        QueryWrapper<File_belong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",file_belong.getUserId())
                .eq("file_hash",file_download.getFileHash())
                .eq("belong_type",file_belong.getBelongType())
                .eq("belong_id",file_belong.getBelongId());
        System.out.println(file_belong);
        Integer count = file_belong.selectCount(queryWrapper);
        if(count==0){
            file_belong.insert();
        }
        map.put("name",file_belong.getName());
        return map;
    }

    /**
     * 上传 富文本 图片
     * @param file
     * @return
     */
    @Override
    public File_download uploadEditorFile(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        File_download file_download = null;
        file_download = qiniuFileUtil.upload(file);
        return file_download;
    }

    /**
     * 删除已经上传的图片
     * @param imgPath
     */
    @Override
    public RestResponse deleteQiniu(String imgPath) {
        return qiniuFileUtil.deleteQiniuP(imgPath);
    }

    @Override
    public String uploadImageSrc(String src) {
        return null;
    }

    @Override
    public String uploadLocalImg(String src) {
        return null;
    }

    /**
     * 上传base64位的图片
     * @param base64
     * @param name
     * @return
     */
    @Override
    public RestResponse uploadBase64(String base64, String name) {
        return qiniuFileUtil.uploadBase64(base64,name);
    }

    /**
     * 上传 学校（S）或教师（T）的背景图片
     * @param file
     * @param type
     * @param id
     * @return
     */
    @Override
    public Map<Object, Object> uploadBackgroundImg(MultipartFile file, String type, Integer id) throws IOException, NoSuchAlgorithmException {
        Map map = Maps.newHashMap();
        File_download file_download = qiniuFileUtil.upload(file);
        map.put("url",file_download.getFileUrl());
        if(type.equals("S")){
            wxSchoolService.updateSchoolBackground(id,file_download.getFileUrl());
        }else if(type.equals("T")){
            wxUsersService.updateTeacherBackground(id,file_download.getFileUrl());
        }
        map.put("name",file_download.getName());
        return map;
    }

    /**
     * 上传 学校（S）或教师（T）的头像图片
     * @param file
     * @param type
     * @param id
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public Map<Object, Object> uploadIconImg(MultipartFile file,String type,Integer id) throws IOException, NoSuchAlgorithmException {
        Map map = Maps.newHashMap();
        File_download file_download = qiniuFileUtil.upload(file);
        map.put("url",file_download.getFileUrl());
        if(type.equals("S")){
            wxSchoolService.updateSchoolHead(id,file_download.getFileUrl());
        }else if(type.equals("T")){
            wxUsersService.updateUserIcon(id,file_download.getFileUrl());
        }
        map.put("name",file_download.getName());
        return map;
    }

    /**
     * 上传单个视频
     * @param file
     * @param video
     * @return
     */
    @Override
    public RestResponse uploadVideo(MultipartFile file, Video video) throws IOException, NoSuchAlgorithmException {
        File_download file_download = qiniuFileUtil.upload(file);
        if(video.getEpisodeId()==null || video.getVideoMainId()==null){
            return RestResponse.failure("参数错误");
        }
        Video video_result = wxVideoService.getVideoByEpisodeId_VideoMainId(video.getEpisodeId(), video.getVideoMainId());
        if(video_result != null){
            /*更新视频播放地址*/
            video_result.setVerifyStatus("审核中");
            video_result.setVideoSource(file_download.getFileUrl());
            video_result.setVideoSize(file_download.getFileSize());
            video_result.setVideoDate(new Date());
            wxVideoService.updateVideoById(video_result);
        }else{
            /*插入新的视频*/
            if(video.getVideoTitle()==null || video.getPublicStatus()==null){
                return RestResponse.failure("参数错误");
            }
            if(video.getVideoDate()==null){
                video.setVideoDate(new Date());
            }
            video.setVerifyStatus("审核中");
            video.setVideoSource(file_download.getFileUrl());
            video.setVideoSize(file_download.getFileSize());

            Integer videoId = wxVideoService.insertVideo(video);
            Map result = Maps.newHashMap();
            result.put("videoId",videoId);
            return RestResponse.success().setData(result);
        }
        return RestResponse.success();
    }


}
