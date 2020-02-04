package com.xvls.alexander.service;


import com.xvls.alexander.entity.File_belong;
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
    public Map<Object,Object> upload(MultipartFile file, File_belong file_belong) throws IOException, NoSuchAlgorithmException;

    /**
     * 删除已经上传的图片
     * @param imgPath
     */
    public void deleteQiniu(String imgPath);

    /**
     * 上传网络图片
     * @param src
     * @return
     */
    public String uploadImageSrc(String src);

    /**
     * 上传本地图片
     * @param src
     * @return
     */
    public String uploadLocalImg(String src);

    /**
     * 上传base64位的图片
     * @param base64
     * @param name
     * @return
     */
    public String uploadBase64(String base64,String name);

}
