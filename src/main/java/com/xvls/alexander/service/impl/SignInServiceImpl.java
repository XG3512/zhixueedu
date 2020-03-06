package com.xvls.alexander.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.xvls.alexander.entity.SignIn_info;
import com.xvls.alexander.entity.SignIn_user;
import com.xvls.alexander.service.SignInService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.QiniuFileUtil;
import com.xvls.alexander.utils.SystemResponse;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 签到
 */
@Service
public class SignInServiceImpl implements SignInService {

    /**
     * 生成签到图片
     * @param signIn_info
     * @return
     */
    @Override
    public String GetSignInImage(SignIn_info signIn_info) throws IOException, NoSuchAlgorithmException {

        Integer siiId = this.insertSignInfo(signIn_info);
        Map result = Maps.newHashMap();
        result.put("siiId",siiId);
        result.put("startTime",signIn_info.getStartTime());
        result.put("endTime",signIn_info.getEndTime());

        QrConfig config = new QrConfig(400, 400);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        BufferedImage generate = QrCodeUtil.generate(JacksonUtil.to(SystemResponse.ok(result)), config);
        String s = QiniuFileUtil.uploadBySystem(generate);
        return s;
    }

    @Override
    public Integer insertSignInfo(SignIn_info signIn_info) {
        boolean insert = signIn_info.insert();
        return signIn_info.getSiiId();
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
