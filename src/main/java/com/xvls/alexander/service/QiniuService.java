package com.xvls.alexander.service;


import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.entity.wx.Video;
import com.xvls.alexander.utils.RestResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface QiniuService {

    /**
     * 普通上传图片
     * @param file
     * @return
     */
    Map<Object,Object> upload(MultipartFile file, File_belong file_belong) throws IOException, NoSuchAlgorithmException;

    /**
     * 上传 富文本 图片
     * @param file
     * @return
     */
    File_download uploadEditorFile(MultipartFile file) throws IOException, NoSuchAlgorithmException;

    /**
     * 删除已经上传的图片
     * @param imgPath
     */
    RestResponse deleteQiniu(String imgPath);

    /**
     * 上传网络图片
     * @param src
     * @return
     */
    String uploadImageSrc(String src);

    /**
     * 上传本地图片
     * @param src
     * @return
     */
    String uploadLocalImg(String src);

    /**
     * 上传base64位的图片
     * @param base64
     * @param name
     * @return
     */
    RestResponse uploadBase64(String base64,String name);

    /**
     * 上传 学校（S）或教师（T）的背景图片
     * @param file
     * @param type
     * @param id
     * @return
     */
    Map<Object, Object> uploadBackgroundImg(MultipartFile file,String type,Integer id) throws IOException, NoSuchAlgorithmException;

    /**
     * 上传 学校（S）或教师（T）的头像图片
     * @param file
     * @param type
     * @param id
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    Map<Object, Object> uploadIconImg(MultipartFile file,String type,Integer id) throws IOException, NoSuchAlgorithmException;

    /**
     * 上传视频
     * @param file
     * @param video
     * @return
     */
    RestResponse uploadVideo(MultipartFile file, Video video) throws IOException, NoSuchAlgorithmException;
}
