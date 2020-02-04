package com.xvls.alexander.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    QiniuFileUtil qiniuFileUtil;

    /**
     * 普通上传图片,需要上传文件,userId,belongType,belongId,name,
     * @param file
     * @param httpServletRequest
     * @return
     */
    @PostMapping("uploadFile")
    public RestResponse uploadFile(@RequestParam("file")MultipartFile file, HttpServletRequest httpServletRequest){
        String getParameter = httpServletRequest.getParameter("file_belong");
        System.out.println(getParameter);
        File_belong file_belong = new Gson().fromJson(getParameter,File_belong.class);
        //System.out.println(file_belong);
        if(file_belong==null){
            return RestResponse.failure("加载信息错误，参数传入错误");
        }
        if(file==null){
            return RestResponse.failure("上传文件为空");
        }
        File_download file_download = null;
        Map map = Maps.newHashMap();
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.failure(e.getMessage());
        }
        return RestResponse.success().setData(map);
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
        return qiniuFileUtil.deleteQiniuP(imgPath);
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
     * @param file_belongs
     * @param files
     * @param httpServletRequest
     * @return
     */
    @PostMapping("uploadManyFile")
    public Map<String,Object> uploadManyFile(@RequestParam("infoms") List<File_belong> file_belongs,
                                             @RequestParam("files")MultipartFile[] files,
                                             HttpServletRequest httpServletRequest){
        System.out.println(file_belongs);
        if(files == null){
            return RestResponse.failure("上传文件不能为空");
        }

        return null;
    }

}
