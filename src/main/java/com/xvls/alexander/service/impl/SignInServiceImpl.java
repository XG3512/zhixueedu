package com.xvls.alexander.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.xvls.alexander.entity.SignIn_info;
import com.xvls.alexander.entity.SignIn_user;
import com.xvls.alexander.service.SignInService;
import com.xvls.alexander.utils.JacksonUtil;
import com.xvls.alexander.utils.QiniuFileUtil;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

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

        QrConfig config = new QrConfig(400, 400);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        BufferedImage generate = QrCodeUtil.generate(JacksonUtil.to(signIn_info.getSiiId()), config);
        String s = QiniuFileUtil.uploadBySystem(generate);
        return s;
    }

    @Override
    public void SignIn(SignIn_user signIn_user) {
        signIn_user.insert();
    }


}
