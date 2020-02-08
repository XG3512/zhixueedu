package com.xvls.alexander.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.xvls.alexander.entity.File_belong;
import com.xvls.alexander.entity.File_download;
import com.xvls.alexander.exception.MyException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

@Component
public class QiniuFileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(QiniuFileUtil.class);

	//@Value("${xvls_qiniu.path}")
	private static String path="http://xvls.top/";

	//@Value("${xvls_qiniu.qiniuAccess}")
	private static String qiniuAccess="fVm1qUAtREn6wyBjzJhB7JE4piRHgzypdtvTTCFV";

	//@Value("${xvls_qiniu.qiniuKey}")
	private static String qiniuKey="3MngPq1ovKCCCjkOwucoQC7Gxg_clZRJMRFTnKKy";

	//@Value("${xvls_qiniu.bucketName}")
	private static String bucketName="test01";

	/***
	 * 普通用户上传图片
	 * @param file
	 * @return
	 * @throws QiniuException
	 * @throws IOException
	 */
	public static File_download upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {
		File_download rescource = new File_download();

		Configuration config = new Configuration(Region.region0());
		String fileName = "", extName = "", filePath = "";
		if (null != file && !file.isEmpty()) {
			extName = file.getOriginalFilename().substring(
					file.getOriginalFilename().lastIndexOf(".")+1);
			fileName = extName + UUID.randomUUID();
			UploadManager uploadManager = new UploadManager(config);
			Auth auth = Auth.create(qiniuAccess, qiniuKey);
			String token = auth.uploadToken(bucketName);
			byte[] data = file.getBytes();
			QETag tag = new QETag();
			String hash = tag.calcETag(file);

			QueryWrapper<File_download> wrapper = new QueryWrapper<>();
			wrapper.eq("file_hash",hash);
			rescource = rescource.selectOne(wrapper);
			//System.out.println("selectOne:"+rescource);
			if( rescource != null){
				return rescource;
			}
			try {
				Response r = uploadManager.put(data, fileName, token);
				//解析上传成功的结果
				DefaultPutRet putRet = new Gson().fromJson(r.bodyString(), DefaultPutRet.class);
				System.out.println(putRet.key);
				System.out.println(putRet.hash);
				if (r.isOK()) {
					filePath = path + fileName;
					rescource = new File_download();
					rescource.setFileName(fileName);
					rescource.setName(file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".")));
					rescource.setFileSize(new java.text.DecimalFormat("#.##").format(file.getSize()/1024)+"kb");
					rescource.setFileHash(hash);
					rescource.setFileType(StringUtils.isBlank(extName)?"unknown":extName);
					rescource.setFileUrl(filePath);
					rescource.setSource("qiniu");
					rescource.setFileDate(new Date());
					rescource.insert();
				}
			} catch (QiniuException e) {
				Response response = e.response;
				System.err.println(response.toString());
				try {
					System.err.println(response.bodyString());
				} catch (QiniuException ex2) {
					//ignore
				}
			}
		}
		return rescource;
	}

	/**
	 * 系统普通上传图片,仅仅将文件上传至七牛云，并设置生存时间，用于二维码生成
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String uploadBySystem(BufferedImage file) throws IOException, NoSuchAlgorithmException {
		Configuration config = new Configuration(Region.region0());
		String fileName = "", extName = "Qrcode", filePath = "";
		if (null != file) {
			fileName = extName + UUID.randomUUID();
			UploadManager uploadManager = new UploadManager(config);
			Auth auth = Auth.create(qiniuAccess, qiniuKey);
			String token = auth.uploadToken(bucketName);
			InputStream data = BufferedImageToInputStream.bufferedImageToInputStream(file);

			try {
				Response r = uploadManager.put(data, fileName, token,null,null);
				//解析上传成功的结果
				DefaultPutRet putRet = new Gson().fromJson(r.bodyString(), DefaultPutRet.class);
				String result_key = putRet.key;
				System.out.println(putRet.key);
				System.out.println(putRet.hash);
				if (r.isOK()) {
					/**设置生存时间*/
					SetFileExistsDays(result_key, 1);
					filePath = path + fileName;
				}
			} catch (QiniuException e) {
				Response response = e.response;
				System.err.println(response.toString());
				try {
					System.err.println(response.bodyString());
				} catch (QiniuException ex2) {
					//ignore
				}
			}
		}
		return filePath;
	}

	/***
	 * 删除已经上传的图片
	 * @param imgPath
	 */
	public static RestResponse deleteQiniuP(String imgPath) {
		Configuration config = new Configuration(Region.region0());
		Auth auth = Auth.create(qiniuAccess, qiniuKey);
		BucketManager bucketManager = new BucketManager(auth,config);
		imgPath = imgPath.replace(path, "");
		try {
			bucketManager.delete(bucketName, imgPath);

			/**
			 * 删除资料记录记录
			 */
			File_download file_download = new File_download();
			QueryWrapper<File_download> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("file_url",path+imgPath);
			File_download file_download1 = file_download.selectOne(queryWrapper);

			File_belong file_belong = new File_belong();
			file_belong.setFileHash(file_download1.getFileHash());
			QueryWrapper<File_belong> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("file_hash",file_belong.getFileHash());
			file_belong.delete(queryWrapper1);

			file_download1.deleteById();
		} catch (QiniuException e) {
			e.printStackTrace();
			return RestResponse.failure("删除失败");
		}
		return RestResponse.success("删除成功");
	}

	/***
	 * 上传网络图片
	 * @param src
	 * @return
	 */
	public static String uploadImageSrc(String src){
		Configuration config = new Configuration(Region.region0());
		Auth auth = Auth.create(qiniuAccess, qiniuKey);
		BucketManager bucketManager = new BucketManager(auth, config);
		String fileName = UUID.randomUUID().toString(),filePath="";
		try {
			FetchRet fetchRet = bucketManager.fetch(src, bucketName);
			filePath = path + fetchRet.key;
			File_download rescource = new File_download();
			rescource.setFileName(fetchRet.key);
			rescource.setFileSize(new java.text.DecimalFormat("#.##").format(fetchRet.fsize/1024)+"kb");
			rescource.setFileHash(fetchRet.hash);
			rescource.setFileType(fetchRet.mimeType);
			rescource.setFileUrl(filePath);
			rescource.setSource("qiniu");
			rescource.setFileDate(new Date());
			rescource.insert();
		} catch (QiniuException e) {
			filePath = src;
			e.printStackTrace();
		}
		return filePath;
	}

	/***
	 * 上传本地图片
	 * @param src
	 * @return
	 */
	public static String uploadLocalImg(String src) throws IOException, NoSuchAlgorithmException{
		Configuration config = new Configuration(Region.region0());
		UploadManager uploadManager = new UploadManager(config);
		Auth auth = Auth.create(qiniuAccess, qiniuKey);
		String token = auth.uploadToken(bucketName);
		File file = new File(src);
		if(!file.exists()){
			throw new MyException("本地文件不存在");
		}
		QETag tag = new QETag();
		String hash = tag.calcETag(file);
		File_download rescource = new File_download();
		QueryWrapper<File_download> wrapper = new QueryWrapper<>();
		wrapper.eq("file_hash",hash);
		rescource = rescource.selectOne(wrapper);
		if( rescource!= null){
			return rescource.getFileUrl();
		}
		String filePath="",
				extName = "",
		name = UUID.randomUUID().toString();
		extName = file.getName().substring(
				file.getName().lastIndexOf("."));
		Response response = uploadManager.put(file,name,token);
		if(response.isOK()){
			filePath = path + name;
			rescource = new File_download();
			rescource.setFileName(name);
			rescource.setFileSize(new java.text.DecimalFormat("#.##").format(file.length()/1024)+"kb");
			rescource.setFileHash(hash);
			rescource.setFileType(StringUtils.isBlank(extName)?"unknown":extName);
			rescource.setFileUrl(filePath);
			rescource.setSource("qiniu");
			rescource.setFileDate(new Date());
			rescource.insert();
		}
		return filePath;
	}

	/**
	 * 上传base64位的图片
	 * @param base64
	 * @return
	 */
	public static RestResponse uploadBase64(String base64,String name) {
		if(base64.isEmpty()){
			return RestResponse.failure("图片不能为空");
		}
		if(name.isEmpty()){
			return RestResponse.failure("图片名称不能为空");
		}
		Configuration config = new Configuration(Region.region0());
		UploadManager uploadManager = new UploadManager(config);
		Auth auth = Auth.create(qiniuAccess, qiniuKey);
		String token = auth.uploadToken(bucketName),filePath;

		byte[] data = Base64.decodeBase64(base64);
		try {
			uploadManager.put(data,name,token);
		} catch (IOException e) {
			e.printStackTrace();
		}
		filePath = path+name;
		return RestResponse.success().setData(filePath);
	}

	/**
	 * 通过键设置或更新文件的生存时间
	 * @param key
	 * @param days 过期天数
	 * @return
	 */
	public static void SetFileExistsDays(String key,int days){
		//构造一个带指定 Region 对象的配置类
		Configuration cfg = new Configuration(Region.region0());
		//...其他参数参考类注释
		//过期天数，该文件10天后删除
		//int days = 10;

		Auth auth = Auth.create(qiniuAccess, qiniuKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
			bucketManager.deleteAfterDays(bucketName, key, days);
		} catch (QiniuException ex) {
			System.err.println(ex.response.toString());
		}
	}


}
