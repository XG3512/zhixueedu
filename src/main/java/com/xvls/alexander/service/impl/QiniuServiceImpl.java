package com.xvls.alexander.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.service.QiniuService;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class QiniuServiceImpl implements QiniuService {

    @Autowired
    QiniuFileUtil qiniuFileUtil;

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
        Integer count = file_belong.selectCount(queryWrapper);
        if(count==0){
            file_belong.insert();
        }
        map.put("name",file_belong.getName());
        return map;
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
}
