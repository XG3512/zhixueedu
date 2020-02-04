package com.xvls.alexander.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.service.QiniuService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    QiniuFileUtil qiniuFileUtil;

    @Autowired
    QiniuService qiniuService;

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

}
