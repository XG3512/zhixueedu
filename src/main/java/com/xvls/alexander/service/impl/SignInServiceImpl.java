package com.xvls.alexander.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.xvls.alexander.dao.SignInInfoMapper;
import com.xvls.alexander.dao.SignInUserMapper;
import com.xvls.alexander.entity.*;
import com.xvls.alexander.service.SignInService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 签到
 */
@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    SignInInfoMapper signInInfoMapper;
    @Autowired
    SignInUserMapper signInUserMapper;

    /**
     * 生成签到图片
     * @param siiId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public BufferedImage GetSignInImage(Integer siiId, Date startTime,Date endTime) throws IOException, NoSuchAlgorithmException {


        Map result = Maps.newHashMap();
        result.put("siiId",siiId);
        result.put("startTime",startTime);
        result.put("endTime",endTime);

        QrConfig config = new QrConfig(400, 400);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(2);
        BufferedImage generate = QrCodeUtil.generate(JacksonUtil.to(SystemResponse.ok(result)), config);
        //String s = QiniuFileUtil.uploadBySystem(generate); //将签到码上传到七牛云

        return generate;
    }

    @Override
    public Integer insertSignInfo(SignIn_info signIn_info) {
        boolean insert = signIn_info.insert();
        return signIn_info.getSiiId();
    }

    /**
     * 通过 siiId 获得签到信息
     * @param siiId
     * @return
     */
    @Override
    public SignIn_info getSignIn_infoBySiiId(Integer siiId) {
        return signInInfoMapper.selectById(siiId);
    }

    /**
     * 通过 pageInfo，userId 获得签到信息列表
     * @param pageInfo
     * @param userId
     * @return
     */
    @Override
    public List<SignIn_infoList> getSignInInfoList(PageInfo pageInfo, Integer userId) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum-1)*pageSize;
        return signInInfoMapper.getSignInInfoList(new PageInfo(pageNum,pageSize),userId);
    }

    /**
     * 通过 siiId 获得 签到学生的列表
     * @param pageInfo
     * @param siiId
     * @return
     */
    @Override
    public List<SignIn_userList> getSignIn_userList(PageInfo pageInfo, Integer siiId) {
        Integer pageNum = pageInfo.getPageNum();
        Integer pageSize = pageInfo.getPageSize();
        pageNum = (pageNum - 1)*pageSize;
        return signInUserMapper.getSignIn_userList(new PageInfo(pageNum,pageSize),siiId);
    }

    /**
     * 通过 siiId 删除对应的SignIn_info
     * @param siiId
     */
    @Override
    public void deleteSignIn_info(Integer siiId) {
        signInInfoMapper.deleteSignIn_info(siiId);
    }

    /**
     * 通过 siiId 删除对应的SignIn_user
     * @param siiId
     */
    @Override
    public void deleteSignIn_user(Integer siiId) {
        signInUserMapper.deleteSignIn_user(siiId);
    }

    /**
     * 插入 signIn_user 信息
     * @param signIn_user
     */
    @Override
    public void SignIn(SignIn_user signIn_user) {
        signIn_user.insert();
    }

    /**
     * 通过 signIn_user 获得对应签到者的数量
     * @param signIn_user
     * @return
     */
    @Override
    public Integer selectSignIn_user(SignIn_user signIn_user) {
        QueryWrapper<SignIn_user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sii_id",signIn_user.getSiiId())
                .eq("user_id",signIn_user.getUserId());
        Integer count = signIn_user.selectCount(queryWrapper);
        return count;
    }


}
